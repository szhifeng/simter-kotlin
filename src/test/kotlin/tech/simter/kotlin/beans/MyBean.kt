package tech.simter.kotlin.beans

/**
 * @author RJ
 */
class MyBean : AbstractDynamicBean() {
  var property1: String? by holder
  var property2: Int? by holder
}