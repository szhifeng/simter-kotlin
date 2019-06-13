package tech.simter.kotlin.beans

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.MappedSuperclass

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
abstract class AbstractDynamicBean : DynamicBean {
  /** Properties data holder */
  @get:JsonIgnore
  @get:javax.persistence.Transient
  @get:org.springframework.data.annotation.Transient
  protected val holder: DataHolder = DataHolder()

  @get:JsonIgnore
  @get:javax.persistence.Transient
  @get:org.springframework.data.annotation.Transient
  override val data: Map<String, Any?>
    get() = holder.map

  override fun toString(): String {
    return "${javaClass.simpleName}=${holder.map}"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as AbstractDynamicBean

    if (holder != other.holder) return false

    return true
  }

  override fun hashCode(): Int {
    return holder.hashCode()
  }
}