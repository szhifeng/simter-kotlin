package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.YearMonth

/**
 * Test [IsoYearMonthSerializer]
 *
 * @author RJ
 */
class IsoYearMonthSerializerTest {
  private val json = Json(Stable.copy(encodeDefaults = false))

  @Serializable
  data class Bean(
    val ps: List<@Serializable(with = IsoYearMonthSerializer::class) YearMonth>,
    @Serializable(with = IsoYearMonthSerializer::class)
    val p1: YearMonth,
    @Serializable(with = IsoYearMonthSerializer::class)
    val p2: YearMonth?,
    @Serializable(with = IsoYearMonthSerializer::class)
    val p3: YearMonth? = null
  )

  @Test
  fun test() {
    val ym = YearMonth.of(2019, 1)
    val str = """{"ps":["2019-01"],"p1":"2019-01","p2":null}"""
    val bean = Bean(ps = listOf(ym), p1 = ym, p2 = null)
    assertThat(json.parse(Bean.serializer(), str)).isEqualTo(bean)
    assertThat(json.stringify(Bean.serializer(), bean)).isEqualTo(str)
  }
}