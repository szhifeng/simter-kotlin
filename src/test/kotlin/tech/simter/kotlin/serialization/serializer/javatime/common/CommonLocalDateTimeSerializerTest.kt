package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

/**
 * Test [CommonLocalDateTimeSerializer]
 *
 * @author RJ
 */
class CommonLocalDateTimeSerializerTest {
  private val stableJson = Json(Stable)

  @Serializable
  data class Bean(
    @Serializable(with = CommonLocalDateTimeSerializer::class)
    val p1: LocalDateTime,
    @Serializable(with = CommonLocalDateTimeSerializer::class)
    val p2: LocalDateTime?,
    @Serializable(with = CommonLocalDateTimeSerializer::class)
    val p3: LocalDateTime? = null
  )

  @Test
  fun test() {
    val str = """{
      "p1": "2019-12-01 10:20:30",
      "p2": null
    }"""
    val b = stableJson.parse(Bean.serializer(), str)
    assertThat(b.p1).isEqualTo(LocalDateTime.of(2019, 12, 1, 10, 20, 30))
    assertThat(b.p2).isNull()
    assertThat(b.p3).isNull()
  }
}