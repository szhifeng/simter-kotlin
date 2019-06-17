package tech.simter.kotlin.beans

import tech.simter.kotlin.annotation.Comment
import tech.simter.kotlin.beans.DynamicBean.PropertyType.*
import tech.simter.util.StringUtils
import tech.simter.util.StringUtils.CaseType
import tech.simter.util.StringUtils.CaseType.Original
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.KVisibility.PUBLIC
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

/**
 * A simple dynamic bean interface to hold the changed property name and its value.
 *
 * Implementation should keep all delegate property delegate to this [DynamicBean.data].
 *
 * @author RJ
 */
interface DynamicBean {
  /** A [Map] hold all the delegate property name and its value that have been set */
  val data: Map<String, Any?>

  /** Use for method [DynamicBean.propertyNames] */
  enum class PropertyType {
    All, Readonly, Writable
  }

  companion object {
    private val excludePropertyNames = listOf("data", "holder")

    fun <T : Any> mapChangedProperties(
      bean: DynamicBean,
      valueEncoder: ((index: Int, value: Any?, p: PropertyInfo) -> String?) = { _, value, _ -> value?.toString() },
      collectionElementMapper: ((index: Int, value: Any?, encodedValue: String?, PropertyInfo) -> T)? = null,
      propertyMapper: (value: Any?, encodedValue: String?, p: PropertyInfo) -> T
    ): List<T> {
      //Set<T>
      val properties: Map<String, PropertyInfo> = properties(clazz = bean.javaClass.kotlin).associate { it.name to it }
      val mapped = mutableListOf<T>()
      var i = 0
      bean.data.forEach { (propertyName, propertyValue) ->
        val p = properties.getValue(propertyName)
        if (collectionElementMapper != null && propertyValue is Collection<*>) { // collection property
          propertyValue.forEachIndexed { index, itemValue ->
            mapped.add(collectionElementMapper.invoke(index, itemValue, valueEncoder(index, itemValue, p), p))
          }
        } else { // simple property
          mapped.add(propertyMapper(propertyValue, valueEncoder(i, propertyValue, p), p))
        }
        i++
      }
      return mapped
    }

    /**
     * Find declared property names of [DynamicBean] sub-class.
     *
     * Default return all custom public property names.
     */
    fun <T : DynamicBean> properties(
      clazz: KClass<T>,
      propertyType: PropertyType = All,
      visibility: KVisibility = PUBLIC,
      predicate: (KProperty<*>) -> Boolean = { true }
    ): List<PropertyInfo> {
      return clazz.memberProperties.filter {
        it.visibility == visibility
          && !excludePropertyNames.contains(it.name)
          && predicate(it)
          && when (propertyType) {
          Writable -> it is KMutableProperty<*>
          Readonly -> it !is KMutableProperty<*>
          else -> true
        }
      }.map {
        PropertyInfo(
          name = it.name,
          comment = it.findAnnotation<Comment>()?.value,
          type = it.returnType.jvmErasure
        )
      }
    }

    /** Convenient method for [properties] */
    inline fun <reified T : DynamicBean> properties(
      propertyType: PropertyType = All,
      visibility: KVisibility = PUBLIC,
      noinline predicate: (KProperty<*>) -> Boolean = { true }
    ): List<PropertyInfo> {
      return properties(
        clazz = T::class,
        propertyType = propertyType,
        visibility = visibility,
        predicate = predicate
      )
    }

    /**
     * Find declared property names of [DynamicBean] sub-class.
     *
     * Default return all custom public property names.
     * If want to return lower-case underscore name, set [underscore] to true.
     */
    fun <T : DynamicBean> propertyNames(
      clazz: KClass<T>,
      propertyType: PropertyType = All,
      visibility: KVisibility = PUBLIC,
      underscore: Boolean = false,
      caseType: CaseType = Original
    ): List<String> {
      val list = clazz.memberProperties.filter {
        it.visibility == visibility
          && !excludePropertyNames.contains(it.name)
          && when (propertyType) {
          Writable -> it is KMutableProperty<*>
          Readonly -> it !is KMutableProperty<*>
          else -> true
        }
      }.map { it.name }
      return if (underscore)
        StringUtils.underscore(source = list.joinToString(","), caseType = caseType).split(",")
      else list
    }

    /** Convenient method for [propertyNames] */
    inline fun <reified T : DynamicBean> propertyNames(
      propertyType: PropertyType = All,
      visibility: KVisibility = PUBLIC,
      underscore: Boolean = false,
      caseType: CaseType = Original
    ): List<String> {
      return propertyNames(
        clazz = T::class,
        propertyType = propertyType,
        visibility = visibility,
        underscore = underscore,
        caseType = caseType
      )
    }

    /**
     * Copy public property from source to target.
     *
     * Only copy writable property of [target] from [source].
     *
     * Return [target] for chained invoke.
     * @param[postProcessor] a post processor after property copied.
     */
    fun <S : DynamicBean, T : DynamicBean> assign(
      target: T,
      source: S,
      postProcessor: (target: T) -> Unit = { _ -> }
    ): T {
      val targetMap = target.data as MutableMap<String, Any?>
      val writablePropertyNames = propertyNames(clazz = target::class, propertyType = Writable)
      source.data.forEach { if (writablePropertyNames.contains(it.key)) targetMap[it.key] = it.value }
      postProcessor(target)
      return target
    }

    /** Convenient method for [assign] */
    inline fun <reified T : DynamicBean> assign(
      source: DynamicBean,
      noinline postProcessor: (target: T) -> Unit = { _ -> }
    ): T {
      val target = T::class.createInstance()
      return assign(source = source,
                    target = target,
                    postProcessor = postProcessor)
    }
  }
}