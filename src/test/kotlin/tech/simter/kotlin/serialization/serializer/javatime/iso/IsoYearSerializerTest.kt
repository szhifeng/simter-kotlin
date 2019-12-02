package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Year

/**
 * Test [IsoYearSerializer]
 *
 * @author RJ
 */
class IsoYearSerializerTest {
  private val stableJson = Json(Stable)

  @Serializable
  data class Bean(
    @Serializable(with = IsoYearSerializer::class)
    val p1: Year,
    @Serializable(with = IsoYearSerializer::class)
    val p2: Year?,
    @Serializable(with = IsoYearSerializer::class)
    val p3: Year? = null
  )

  @Test
  fun test() {
    val str = """{
      "p1": "2019",
      "p2": null
    }"""
    val b = stableJson.parse(Bean.serializer(), str)
    assertThat(b.p1).isEqualTo(Year.of(2019))
    assertThat(b.p2).isNull()
    assertThat(b.p3).isNull()
  }
}