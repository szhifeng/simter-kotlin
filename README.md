# simter-kotlin [[中文]]

Simter kotlin extension. 

## 1. [DynamicBean] class

A super class for define a dynamic bean by inheritance.

1. Use pattern `'var {propertyName}: {valueType}? by holder'` to define a dynamic property in your dynamic bean class.
2. To define a dynamic property, the subclass property must be mutable (var), nullable (T?) and no default value.
3. The `data` property holds all settled dynamic property name and value. By default, it's a empty `Map`.

```kotlin
class MyBean : DynamicBean() {
  var property1: String? by holder
  var property2: Int? by holder
}

@Test
fun test() {
  val bean = MyBean()
  assertNotNull(bean.data)
  assertEquals(0, bean.data.size)

  // default value is null if not settled
  assertNull(bean.data["property1"])

  // set null value
  bean.property1 = null
  assertEquals(1, bean.data.size)
  assertNull(bean.data["property1"])

  // set not null value
  bean.property1 = "value1"
  assertEquals(1, bean.data.size)
  assertEquals("value1", bean.data["property1"])

  // set another property
  bean.property2 = 123
  assertEquals(2, bean.data.size)
  assertEquals("value1", bean.data["property1"])
  assertEquals(123, bean.data["property2"])
}
```


[DynamicBean]: https://github.com/simter/simter-kotlin/blob/master/src/main/kotlin/tech/simter/kotlin/DynamicBean.kt
[中文]: https://github.com/simter/simter-kotlin/blob/master/docs/README.zh-cn.md