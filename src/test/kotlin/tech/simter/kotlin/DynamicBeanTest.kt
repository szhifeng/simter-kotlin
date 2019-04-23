package tech.simter.kotlin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tech.simter.kotlin.DynamicBean.CaseType.UpperCase
import tech.simter.kotlin.DynamicBean.Companion.assign
import tech.simter.kotlin.DynamicBean.Companion.propertyNames
import tech.simter.kotlin.DynamicBean.Companion.underscore
import tech.simter.kotlin.DynamicBean.Companion.verifySameNamePropertyHasSameValue
import tech.simter.kotlin.DynamicBean.PropertyType.Readonly
import tech.simter.kotlin.DynamicBean.PropertyType.Writable

/**
 * Test [DynamicBean].
 *
 * @author RJ
 */
class DynamicBeanTest {
  @Test
  fun `no data`() {
    val bean = MyBean()
    assertNotNull(bean.data)
    assertEquals(0, bean.data.size)

    // default value is null
    assertNull(bean.data["property1"])
  }

  @Test
  fun `has data`() {
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

  @Test
  fun `public properties`() {
    // A
    var writableNames = propertyNames<A>(Writable)
    assertEquals(1, writableNames.size)
    assertEquals("wa1", writableNames[0])

    var readonlyNames = propertyNames<A>(Readonly)
    assertEquals(1, readonlyNames.size)
    assertEquals("ra1", readonlyNames[0])

    var allNames = propertyNames<A>()
    assertEquals(2, allNames.size)
    assertTrue(allNames.contains("wa1"))
    assertTrue(allNames.contains("ra1"))

    // B
    writableNames = propertyNames<B>(Writable)
    assertEquals(2, writableNames.size)
    assertTrue(writableNames.contains("wa1"))
    assertTrue(writableNames.contains("wb1"))

    readonlyNames = propertyNames<B>(Readonly)
    assertEquals(2, readonlyNames.size)
    assertTrue(readonlyNames.contains("ra1"))
    assertTrue(readonlyNames.contains("rb1"))

    allNames = propertyNames<B>()
    assertEquals(4, allNames.size)
    assertTrue(allNames.contains("wa1"))
    assertTrue(allNames.contains("wb1"))
    assertTrue(allNames.contains("ra1"))
    assertTrue(allNames.contains("rb1"))
  }

  open class A : DynamicBean() {
    var wa1: String? by holder
    val ra1: String? by holder
    private var wa2: String? by holder
    private val ra2: String? by holder
    protected var wa3: String? by holder
    protected val ra3: String? by holder
  }

  open class B : A() {
    var wb1: String? by holder
    val rb1: String? by holder
    private var wb2: String? by holder
    private val rb2: String? by holder
    protected var wb3: String? by holder
    protected val rb3: String? by holder
  }

  @Test
  fun `assign to target instance`() {
    // copy from A to A
    var source = A().apply { wa1 = "wa1" }
    var target = A()
    assertNotEquals(source, target)
    assign(target, source)
    assertEquals(source, target)

    // copy from A to B
    source = A().apply { wa1 = "wa1" }
    target = B()
    assertNull(target.wa1)
    assign(target, source)
    assertNotNull(target.wa1)
    assertEquals(source.wa1, target.wa1)

    // copy from B to A
    source = B().apply { wa1 = "wa1" }
    target = A()
    assertNull(target.wa1)
    assign(target, source)
    assertNotNull(target.wa1)
    assertEquals(source.wa1, target.wa1)
  }

  @Test
  fun `assign to target type`() {
    // copy from A to A
    var source = A().apply { wa1 = "wa1" }
    var target = assign<A>(source)
    assertEquals(source, target)

    // copy from A to B
    source = A().apply { wa1 = "wa1" }
    target = assign<B>(source)
    assertEquals(source.wa1, target.wa1)

    // copy from B to A
    source = B().apply { wa1 = "wa1" }
    target = assign<B>(source)
    assertEquals(source.wa1, target.wa1)
  }

  @Test
  fun `verify property`() {
    // verify between A and A
    var source = A().apply { wa1 = "wa1" }
    var target = A()
    assertThrows(IllegalStateException::class.java) {
      verifySameNamePropertyHasSameValue(source, target)
    }
    assign(target, source)
    verifySameNamePropertyHasSameValue(source, target)

    // verify between A and B
    source = A().apply { wa1 = "wa1" }
    target = B()
    assertThrows(IllegalStateException::class.java) {
      verifySameNamePropertyHasSameValue(source, target)
    }
    assign(target, source)
    verifySameNamePropertyHasSameValue(source, target)

    // verify between B and A
    source = B().apply { wa1 = "wa1" }
    target = A()
    assertThrows(IllegalStateException::class.java) {
      verifySameNamePropertyHasSameValue(source, target)
    }
    assign(target, source)
    verifySameNamePropertyHasSameValue(source, target)
  }

  @Test
  fun `underscore - lower-case`() {
    assertEquals("my_work", underscore("myWork"))
    assertEquals("my_work", underscore("MyWork"))

    assertEquals("my_office_work", underscore("myOfficeWork"))
    assertEquals("my_office_work", underscore("MyOfficeWork"))

    assertEquals("a", underscore("a"))
    assertEquals("a", underscore("A"))
    assertEquals("abc", underscore("Abc"))
    assertEquals("abc", underscore("ABC"))
    assertEquals("ab_car", underscore("ABCar"))
    assertEquals("user_dto4_form", underscore("UserDto4Form"))
  }

  @Test
  fun `underscore - upper-case`() {
    assertEquals("MY_WORK", underscore(source = "myWork", caseType = UpperCase))
    assertEquals("MY_WORK", underscore(source = "MyWork", caseType = UpperCase))

    assertEquals("MY_OFFICE_WORK", underscore(source = "myOfficeWork", caseType = UpperCase))
    assertEquals("MY_OFFICE_WORK", underscore(source = "MyOfficeWork", caseType = UpperCase))

    assertEquals("A", underscore(source = "a", caseType = UpperCase))
    assertEquals("A", underscore(source = "A", caseType = UpperCase))
    assertEquals("ABC", underscore(source = "Abc", caseType = UpperCase))
    assertEquals("ABC", underscore(source = "ABC", caseType = UpperCase))
    assertEquals("AB_CAR", underscore(source = "ABCar", caseType = UpperCase))
    assertEquals("USER_DTO4_FORM", underscore(source = "UserDto4Form", caseType = UpperCase))
  }
}