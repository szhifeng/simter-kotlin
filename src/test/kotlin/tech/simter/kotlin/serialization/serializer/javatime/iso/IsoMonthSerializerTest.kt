package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Month

/**
 * Test [IsoMonthSerializer]
 *
 * @author RJ
 */
class IsoMonthSerializerTest {
  private val stableJson = Json(Stable)

  @Serializable
  data class Bean(
    @Serializable(with = IsoMonthSerializer::class)
    val p1: Month,
    @Serializable(with = IsoMonthSerializer::class)
    val p2: Month?,
    @Serializable(with = IsoMonthSerializer::class)
    val p3: Month? = null
  )

  @Test
  fun test() {
    val str = """{
      "p1": "12",
      "p2": null
    }"""
    val b = stableJson.parse(Bean.serializer(), str)
    assertThat(b.p1).isEqualTo(Month.of(12))
    assertThat(b.p2).isNull()
    assertThat(b.p3).isNull()
  }
}