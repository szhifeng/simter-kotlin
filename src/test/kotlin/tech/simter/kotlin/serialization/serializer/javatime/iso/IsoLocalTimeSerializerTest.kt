package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalTime

/**
 * Test [IsoLocalTimeSerializer]
 *
 * @author RJ
 */
class IsoLocalTimeSerializerTest {
  private val stableJson = Json(Stable)

  @Serializable
  data class Bean(
    @Serializable(with = IsoLocalTimeSerializer::class)
    val p1: LocalTime,
    @Serializable(with = IsoLocalTimeSerializer::class)
    val p2: LocalTime?,
    @Serializable(with = IsoLocalTimeSerializer::class)
    val p3: LocalTime? = null
  )

  @Test
  fun test() {
    val str = """{
      "p1": "10:20:30",
      "p2": null
    }"""
    val b = stableJson.parse(Bean.serializer(), str)
    assertThat(b.p1).isEqualTo(LocalTime.of(10, 20, 30))
    assertThat(b.p2).isNull()
    assertThat(b.p3).isNull()
  }
}