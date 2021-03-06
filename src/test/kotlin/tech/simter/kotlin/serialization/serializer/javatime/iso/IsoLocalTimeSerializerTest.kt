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
  private val json = Json(Stable.copy(encodeDefaults = false))

  @Serializable
  data class Bean(
    val ps: List<@Serializable(with = IsoLocalTimeSerializer::class) LocalTime>,
    @Serializable(with = IsoLocalTimeSerializer::class)
    val p1: LocalTime,
    @Serializable(with = IsoLocalTimeSerializer::class)
    val p2: LocalTime?,
    @Serializable(with = IsoLocalTimeSerializer::class)
    val p3: LocalTime? = null
  )

  @Test
  fun test() {
    val t = LocalTime.of(1, 20, 59)
    val str = """{"ps":["01:20:59"],"p1":"01:20:59","p2":null}"""
    val bean = Bean(ps = listOf(t), p1 = t, p2 = null)
    assertThat(json.parse(Bean.serializer(), str)).isEqualTo(bean)
    assertThat(json.stringify(Bean.serializer(), bean)).isEqualTo(str)
  }
}