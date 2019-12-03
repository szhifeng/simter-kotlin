package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.MonthDay

/**
 * Test [IsoMonthDaySerializer]
 *
 * @author RJ
 */
class IsoMonthDaySerializerTest {
  private val stableJson = Json(Stable)

  @Serializable
  data class Bean(
    @Serializable(with = IsoMonthDaySerializer::class)
    val p1: MonthDay,
    @Serializable(with = IsoMonthDaySerializer::class)
    val p2: MonthDay?,
    @Serializable(with = IsoMonthDaySerializer::class)
    val p3: MonthDay? = null
  )

  @Test
  fun test() {
    val str = """{
      "p1": "12-01",
      "p2": null
    }"""
    val b = stableJson.parse(Bean.serializer(), str)
    assertThat(b.p1).isEqualTo(MonthDay.of(12, 1))
    assertThat(b.p2).isNull()
    assertThat(b.p3).isNull()
  }
}