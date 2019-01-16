package tech.simter.kotlin.annotation

import kotlin.annotation.AnnotationTarget.*

/**
 * Define a description on the target class, property or field.
 *
 * @author RJ
 */
@Target(
  CLASS, PROPERTY, FIELD, PROPERTY_GETTER, PROPERTY_SETTER,
  CONSTRUCTOR, TYPE, TYPE_PARAMETER, VALUE_PARAMETER
)
@MustBeDocumented
annotation class Comment(val value: String)