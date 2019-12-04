package tech.simter.kotlin.serialization.serializer.javatime

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Year

/**
 * Test [YearSerializer]
 *
 * @author RJ
 */
class YearSerializerTest {
  private val json = Json(Stable.copy(encodeDefaults = false))

  @Serializable
  data class Bean(
    val ps: List<@Serializable(with = YearSerializer::class) Year>,
    @Serializable(with = YearSerializer::class)
    val p1: Year,
    @Serializable(with = YearSerializer::class)
    val p2: Year?,
    @Serializable(with = YearSerializer::class)
    val p3: Year? = null
  )

  @Test
  fun test() {
    val year = Year.of(2019)
    val str = """{"ps":[2019],"p1":2019,"p2":null}"""
    val bean = Bean(ps = listOf(year), p1 = year, p2 = null)
    assertThat(json.parse(Bean.serializer(), str)).isEqualTo(bean)
    assertThat(json.stringify(Bean.serializer(), bean)).isEqualTo(str)
  }
}