package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

/**
 * Test [IsoLocalDateSerializer]
 *
 * @author RJ
 */
class IsoLocalDateSerializerTest {
  private val stableJson = Json(Stable)

  @Serializable
  data class Bean(
    @Serializable(with = IsoLocalDateSerializer::class)
    val p1: LocalDate,
    @Serializable(with = IsoLocalDateSerializer::class)
    val p2: LocalDate?,
    @Serializable(with = IsoLocalDateSerializer::class)
    val p3: LocalDate? = null
  )

  @Test
  fun test() {
    val str = """{
      "p1": "2019-12-01",
      "p2": null
    }"""
    val b = stableJson.parse(Bean.serializer(), str)
    assertThat(b.p1).isEqualTo(LocalDate.of(2019, 12, 1))
    assertThat(b.p2).isNull()
    assertThat(b.p3).isNull()
  }
}