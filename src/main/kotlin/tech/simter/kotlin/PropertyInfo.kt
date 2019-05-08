package tech.simter.kotlin

import tech.simter.kotlin.annotation.Comment
import kotlin.reflect.KClass

/**
 * Property information encapsulation.
 */
data class PropertyInfo(
  /** property's name */
  val name: String,
  /** property's comment value from annotation [Comment] */
  val comment: String?,
  /** property's type */
  val type: KClass<*>
) {
  /** property's type qualified name */
  val typeName: String = type.qualifiedName!!
}