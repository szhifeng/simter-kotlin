package tech.simter.kotlin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * Test [DynamicBean].
 *
 * @author RJ
 */
class DynamicBeanTest {
  @Test
  fun `No data`() {
    val bean = MyBean()
    assertNotNull(bean.data)
    assertEquals(0, bean.data.size)

    // default value is null
    assertNull(bean.data["property1"])
  }

  @Test
  fun `Has data`() {
    val bean = MyBean()

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
}