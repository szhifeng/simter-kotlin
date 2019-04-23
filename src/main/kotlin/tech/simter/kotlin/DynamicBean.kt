package tech.simter.kotlin

import com.fasterxml.jackson.annotation.JsonIgnore
import tech.simter.kotlin.DynamicBean.CaseType.*
import tech.simter.kotlin.DynamicBean.PropertyType.Writable
import javax.persistence.MappedSuperclass
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.KVisibility.PUBLIC
import kotlin.reflect.full.memberProperties

/**
 * A super class for define a dynamic bean by inheritance.
 *
 * 1. Use pattern `'var {propertyName}: {valueType}? by holder'` to define a dynamic property.
 * 2. To define a dynamic property, the subclass property must be mutable (var), nullable (T?) and no default value.
 * 3. The [data] property holds all settled dynamic property name and value. By default, it's a empty [Map].
 *
 * Example :
 * ```
 * class MyBean : DynamicBean() {
 *   var property1: String? by holder
 *   var property2: Int? by holder
 * }
 *
 * @Test
 * fun test() {
 *   val bean = MyBean()
 *   assertNotNull(bean.data)
 *   assertEquals(0, bean.data.size)
 *
 *   // default value is null if not settled
 *   assertNull(bean.data["property1"])
 *
 *   // set null value
 *   bean.property1 = null
 *   assertEquals(1, bean.data.size)
 *   assertNull(bean.data["property1"])
 *
 *   // set not null value
 *   bean.property1 = "value1"
 *   assertEquals(1, bean.data.size)
 *   assertEquals("value1", bean.data["property1"])
 *
 *   // set another property
 *   bean.property2 = 123
 *   assertEquals(2, bean.data.size)
 *   assertEquals("value1", bean.data["property1"])
 *   assertEquals(123, bean.data["property2"])
 * }
 * ```
 *
 * @author RJ
 */
@MappedSuperclass
open class DynamicBean {
  /** Properties data holder */
  @get:JsonIgnore
  @get:javax.persistence.Transient
  @get:org.springframework.data.annotation.Transient
  protected val holder: DataHolder = DataHolder()

  @get:JsonIgnore
  @get:javax.persistence.Transient
  @get:org.springframework.data.annotation.Transient
  val data: Map<String, Any?>
    get() = holder.map

  override fun toString(): String {
    return "${javaClass.simpleName}=${holder.map}"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as DynamicBean

    if (holder != other.holder) return false

    return true
  }

  override fun hashCode(): Int {
    return holder.hashCode()
  }

  /**
   * The data holder for hold the dynamic property name and value.
   *
   * All settled properties will be stored in a [MutableMap]. A [map] property return these stored data.
   * The `key` is the property's name, the `value` is the property's value.
   * If never set a property's value, a null value will be returned.
   *
   * See [Kotlin Delegated Properties](https://kotlinlang.org/docs/reference/delegated-properties.html).
   *
   * @author RJ
   */
  data class DataHolder(
    private val data: MutableMap<String, Any?> = mutableMapOf<String, Any?>().withDefault { null }
  ) {
    @Suppress("UNCHECKED_CAST")
    operator fun <T> getValue(thisRef: Any?, property: KProperty<*>): T {
      return data[property.name] as T
    }

    operator fun <T> setValue(thisRef: Any?, property: KProperty<*>, value: T) {
      data[property.name] = value
    }

    val map: Map<String, Any?> get() = data

    override fun toString(): String {
      return data.toString()
    }
  }

  /** Use for method [DynamicBean.propertyNames] */
  enum class PropertyType {
    All, Readonly, Writable
  }

  /** Use for method [DynamicBean.underscore] and [DynamicBean.propertyNames] */
  enum class CaseType {
    LowerCase, UpperCase, Ignore
  }

  companion object {
    private val regex = Regex("([A-Z][a-z]+)")

    /**
     * Convert a came-case string to a underscore string.
     *
     * Default return lower-case string. Use [caseType] to change it.
     *
     * Default examples:
     *
     * - "a" | "A"  to a
     * - "ABC"  to abc
     * - "ABCar"  to ab_car
     * - "myWork" | "MyWork"  to my_work
     * - "myOfficeWork" | "MyOfficeWork" to my_office_work
     */
    fun underscore(source: String, caseType: CaseType = LowerCase): String {
      val underscore = regex.replace(source.decapitalize()) { "_${it.value}" }
      return when (caseType) {
        LowerCase -> underscore.toLowerCase()
        UpperCase -> underscore.toUpperCase()
        else -> underscore
      }
    }

    private val excludePropertyNames = listOf("data", "holder")

    /**
     * Find declared property names of [DynamicBean] sub-class.
     *
     * Default return all custom public property names.
     * If want to return lower-case underscore name, set [underscore] to true.
     */
    fun <T : DynamicBean> propertyNames(
      clazz: KClass<T>,
      propertyType: PropertyType = PropertyType.All,
      visibility: KVisibility = PUBLIC,
      underscore: Boolean = false,
      caseType: CaseType = Ignore
    ): List<String> {
      val list = clazz.memberProperties.filter {
        it.visibility == visibility
          && !excludePropertyNames.contains(it.name)
          && when (propertyType) {
          Writable -> it is KMutableProperty<*>
          PropertyType.Readonly -> it !is KMutableProperty<*>
          else -> true
        }
      }.map { it.name }
      return if (underscore)
        underscore(source = list.joinToString(","), caseType = caseType).split(",")
      else list
    }

    /** Convenient method for [propertyNames] */
    inline fun <reified T : DynamicBean> propertyNames(
      propertyType: PropertyType = PropertyType.All,
      visibility: KVisibility = PUBLIC,
      underscore: Boolean = false,
      caseType: CaseType = Ignore
    ): List<String> {
      return propertyNames(
        clazz = T::class,
        propertyType = propertyType,
        visibility = visibility,
        underscore = underscore,
        caseType = caseType
      )
    }
  }
}