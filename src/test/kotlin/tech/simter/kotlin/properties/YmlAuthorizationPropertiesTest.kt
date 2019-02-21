package tech.simter.kotlin.properties

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.simter.kotlin.properties.AuthorizeRole.Strategy.And
import tech.simter.kotlin.properties.AuthorizeRole.Strategy.Or

/**
 * @author RJ
 */
@SpringBootTest(classes = [YmlAuthorizationPropertiesTest.Cfg::class])
open class YmlAuthorizationPropertiesTest @Autowired constructor(
  @Value("#{authorizeOperations.operations}")
  private val defaultOperations: Map<String, AuthorizeRole>,
  @Value("#{authorizeModuleOperations.operations}")
  private val moduleOperations: Map<String, Map<String, AuthorizeRole>>
) {
  @Configuration
  @EnableConfigurationProperties
  open class Cfg {
    @Bean
    @ConfigurationProperties(prefix = "module.authorization.x")
    open fun authorizeOperations(): AuthorizeOperations {
      return AuthorizeOperations()
    }

    @Bean
    @ConfigurationProperties(prefix = "module.authorization.y")
    open fun authorizeModuleOperations(): AuthorizeModuleOperations {
      return AuthorizeModuleOperations()
    }
  }

  // see 'module.authorization.x'
  @Test
  fun x() {
    assertNotNull(defaultOperations)
    assertEquals(3, defaultOperations.size)
    assertEquals(AuthorizeRole(strategy = Or, roles = listOf("R1", "R2")), defaultOperations["read"])
    assertEquals(AuthorizeRole(roles = listOf("R1", "R2")), defaultOperations["delete"])
    assertEquals(AuthorizeRole(strategy = And, roles = listOf("R1", "R2")), defaultOperations["create,update"])
  }

  // see 'module.authorization.y'
  @Test
  fun y() {
    assertNotNull(moduleOperations)
    assertEquals(2, moduleOperations.size)
    val operations = mapOf(
      "read" to AuthorizeRole(strategy = Or, roles = listOf("R1", "R2")),
      "delete" to AuthorizeRole(roles = listOf("R1", "R2")),
      "create,update" to AuthorizeRole(strategy = And, roles = listOf("R1", "R2"))
    )
    assertEquals(operations, moduleOperations["default"])
    assertEquals(operations, moduleOperations["m1"])
  }
}