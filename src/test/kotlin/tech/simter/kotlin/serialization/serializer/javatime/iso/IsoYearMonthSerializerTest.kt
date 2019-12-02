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
  private val stableJson = Json(Stable)

  @Serializable
  data class Bean(
    @Serializable(with = IsoYearMonthSerializer::class)
    val p1: YearMonth,
    @Serializable(with = IsoYearMonthSerializer::class)
    val p2: YearMonth?,
    @Serializable(with = IsoYearMonthSerializer::class)
    val p3: YearMonth? = null
  )

  @Test
  fun test() {
    val str = """{
      "p1": "2019-12",
      "p2": null
    }"""
    val b = stableJson.parse(Bean.serializer(), str)
    assertThat(b.p1).isEqualTo(YearMonth.of(2019, 12))
    assertThat(b.p2).isNull()
    assertThat(b.p3).isNull()
  }
}