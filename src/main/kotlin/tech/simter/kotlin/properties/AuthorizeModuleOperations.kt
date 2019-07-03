package tech.simter.kotlin.properties

/**
 * Authorization properties config for multiple sub-modules, 'default' for missing config modules properties.
 *
 * Sample :
 *
 * ```
 * module.authorization.x:
 *   operations:
 *     default:
 *       read:
 *         roles: ['R1', 'R2']
 *         strategy: 'or'
 *       "[create,update]":
 *         roles: ['R1', 'R2']
 *         strategy: 'and'
 *       delete:
 *         roles: ['R1', 'R2']
 *     m1:
 *       read:
 *         roles: ['R1', 'R2']
 *         strategy: 'or'
 *       "[create,update]":
 *         roles: ['R1', 'R2']
 *         strategy: 'and'
 *       delete:
 *         roles: ['R1', 'R2']
 * ```
 * @author RJ
 * @see [AuthorizeOperations]
 */
@Deprecated("use simter-reactive-security/tech.simter.reactive.security.properties.ModuleAuthorizeProperties")
data class AuthorizeModuleOperations(
  /** key is the module identity, value is a nested map.
   *
   * The nested map's key is the operation identity, the nested map's value is the role config.
   */
  val operations: Map<String, Map<String, AuthorizeRole>>? = HashMap()
)