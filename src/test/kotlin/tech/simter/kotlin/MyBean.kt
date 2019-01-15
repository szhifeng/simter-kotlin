package tech.simter.kotlin

/**
 * @author RJ
 */
class MyBean : DynamicBean() {
  var property1: String? by holder
  var property2: Int? by holder
}