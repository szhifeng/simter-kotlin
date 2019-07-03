package tech.simter.kotlin.properties

/**
 * Simple authorization config properties.
 *
 * Sample :
 *
 * ```
 * module.authorization.x:
 *   operations:
 *     read:
 *       roles: ['R1', 'R2']
 *       strategy: 'Or'
 *     "[create,update]":           #   create or update
 *       roles: ['R1', 'R2']
 *       strategy: 'And'
 *     delete:
 *       roles: ['R1', 'R2']
 * ```
 * @author RJ
 * @see [AuthorizeModuleOperations]
 */
@Deprecated("use simter-reactive-security/tech.simter.reactive.security.properties.ModuleAuthorizeProperties")
data class AuthorizeOperations(
  /** key is the operation identity, value is the role config */
  val operations: Map<String, AuthorizeRole> = HashMap()
)