package tech.simter.kotlin.properties

/**
 * Authorize role properties.
 *
 * Sample :
 *
 * ```
 * module.authorization.x:
 *   operations:
 *     read:
 *       roles: ['R1', 'R2']  # AuthorizeRole.roles
 *       strategy: 'Or'       # AuthorizeRole.strategy, 'Or' or 'And', default value is 'Or'
 * ```
 * @author RJ
 * @see [AuthorizeModuleOperations]
 * @see [AuthorizeOperations]
 */
@Deprecated("use simter-reactive-security/tech.simter.reactive.security.properties.ModuleAuthorizeProperties")
data class AuthorizeRole(
  val roles: List<String> = mutableListOf(),
  var strategy: Strategy = Strategy.Or
) {
  enum class Strategy {
    /** Must has all roles */
    And,
    /** Should has any roles */
    Or
  }
}