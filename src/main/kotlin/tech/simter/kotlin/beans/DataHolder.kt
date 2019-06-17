package tech.simter.kotlin.beans

import kotlin.reflect.KProperty

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