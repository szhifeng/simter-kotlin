package tech.simter.kotlin.properties

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration

/**
 * @author RJ
 */
//@SpringJUnitConfig
//@TestPropertySource(locations = ["application.yml"])
@SpringBootTest(classes = [YmlBasePropertiesTest.Cfg::class])
open class YmlBasePropertiesTest @Autowired constructor(
  // simple value
  @Value("\${my.string-value}") private val stringValue: String, // = "string"
  @Value("\${my.int-value}") private val intValue: Int,          // = 1
  @Value("\${my.bool-value}") private val boolValue: Boolean,    // = true

  // list value
  @Value("\${my.list-value1}") private val listValue1: List<String>, // invalid = "[${my.list-value1}]"
  @Value("\${my.list-value2}") private val listValue2: List<String>, // invalid = "[${my.list-value2}]"
  @Value("\${my.list-value2}") private val listValue21: String,      // invalid = "${my.list-value2}"
  @Value("\${my.list-value3}") private val listValue3: String,       // = "1, 2"

  // bean value
  private val my: My
) {
  @Configuration
  @EnableConfigurationProperties(My::class)
  open class Cfg

  @Test
  fun simpleValue() {
    assertEquals("string", stringValue)
    assertEquals(1, intValue)
    assertTrue(boolValue)
  }

  @Test
  fun listValue() {
    // my.list-value1:
    //   - 1
    //   - 2
    assertEquals(java.util.ArrayList::class.java, listValue1.javaClass)
    assertEquals(1, listValue1.size)
    assertEquals("\${my.list-value1}", listValue1[0])

    // my.list-value2=[1, 2]
    assertEquals(java.util.ArrayList::class.java, listValue2.javaClass)
    assertEquals(1, listValue2.size)
    assertEquals("\${my.list-value2}", listValue2[0])

    assertEquals("\${my.list-value2}", listValue21)

    // my.list-value3=1,2
    assertEquals("1, 2", listValue3)
  }

  @Test
  fun beanValue() {
    assertNull(my.notExists)
    assertEquals(1, my.intValue)
    assertEquals(1, my.intValue)
    assertTrue(my.boolValue!!)

    // my.list-value1:
    //   - 1
    //   - 2
    assertNotNull(my.listValue1)
    assertEquals(2, my.listValue1.size)
    assertEquals("1", my.listValue1[0])
    assertEquals("2", my.listValue1[1])

    // my.list-value1-as-int:
    //   - 1
    //   - 2
    assertNotNull(my.listValue1AsInt)
    assertEquals(2, my.listValue1AsInt!!.size)
    assertEquals(1, my.listValue1AsInt!![0])
    assertEquals(2, my.listValue1AsInt!![1])

    // my.list-value2: [1, 2]
    assertNotNull(my.listValue2)
    assertEquals(2, my.listValue2!!.size)
    assertEquals(1, my.listValue2!![0])
    assertEquals(2, my.listValue2!![1])

    // my.list-value3: 1, 2
    assertNotNull(my.listValue3)
    assertEquals(2, my.listValue3!!.size)
    assertEquals(1, my.listValue3!![0])
    assertEquals(2, my.listValue3!![1])

    // my.map-value1:
    //   k1: v1
    //   k2: v2
    assertNotNull(my.mapValue1)
    assertEquals(2, my.mapValue1.size)
    assertEquals("v1", my.mapValue1["k1"])
    assertEquals("v2", my.mapValue1["k2"])

    // my.list-value4: (Set)
    //   - id: 1
    //      name: n1
    //   - id: 2
    //      name: n2
    assertNotNull(my.listValue4)
    assertEquals(2, my.listValue4!!.size)
    assertTrue(my.listValue4!!.contains(IdName(id = 1, name = "n1")))
    assertTrue(my.listValue4!!.contains(IdName(id = 2, name = "n2")))

    // my.map-value2:
    //   k1:
    //     id: 1
    //     name: n1
    //   k2:
    //     id: 2
    //     name: n2
    assertNotNull(my.mapValue2)
    assertEquals(2, my.mapValue2!!.size)
    assertEquals(IdName(id = 1, name = "n1"), my.mapValue2!!["k1"])
    assertEquals(IdName(id = 2, name = "n2"), my.mapValue2!!["k2"])
  }
}

// 1. All properties data class need a no args constructor for java
// 2. list, set, Map property:
//    2.1. define var with default null value
//    2.2. or define val with init a empty mutable List, Set or Map

@ConfigurationProperties(prefix = "my")
data class My(
  var intValue: Int? = null,
  var boolValue: Boolean? = null,
  var stringValue: String? = null,
  val listValue1: List<String> = mutableListOf(),
  var listValue1AsInt: List<Int>? = null,
  var listValue2: List<Int>? = null,
  var listValue3: List<Int>? = null,
  var listValue4: Set<IdName>? = null, // Set
  var mapValue1: Map<String, String> = mutableMapOf(),
  var mapValue2: Map<String, IdName>? = null,
  var notExists: String? = null
)

data class IdName(
  var id: Int? = null,
  var name: String? = null
)