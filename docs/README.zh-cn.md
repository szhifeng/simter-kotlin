# simter-kotlin [[English]]

Simter kotlin 扩展。

## 1. [DynamicBean] 类

一个通过继承此类来定义动态 Bean 的超类。

1. 使用类似 `'var {propertyName}: {valueType}? by holder'` 的形式在动态 Bean 内定义动态属性。
2. 要定义一个动态属性，该子类的属性必需定义为可变（var）、可空（T?） 和没有默认值。
3. 通过属性 `data` 可以获取到所有已经设置过的动态属性的名称和值，默认情况下，此属性是一个空的 `Map'。

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

  // 如果属性没有被设置过默认返回 null 值
  assertNull(bean.data["property1"])

  // 可以设置 null 值
  bean.property1 = null
  assertEquals(1, bean.data.size)
  assertNull(bean.data["property1"])

  // 设置非 null 值
  bean.property1 = "value1"
  assertEquals(1, bean.data.size)
  assertEquals("value1", bean.data["property1"])

  // 设置另一个动态属性
  bean.property2 = 123
  assertEquals(2, bean.data.size)
  assertEquals("value1", bean.data["property1"])
  assertEquals(123, bean.data["property2"])
}
```


[DynamicBean]: https://github.com/simter/simter-kotlin/blob/master/src/main/kotlin/tech/simter/kotlin/DynamicBean.kt
[English]: https://github.com/simter/simter-kotlin/blob/master/README.md