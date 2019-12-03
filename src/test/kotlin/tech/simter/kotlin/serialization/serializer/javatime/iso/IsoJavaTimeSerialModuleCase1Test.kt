package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.*

/**
 * Test [IsoJavaTimeSerialModule]
 *
 * @author RJ
 */
class IsoJavaTimeSerialModuleCase1Test {
  // See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/custom_serializers.md#contextualserialization-annotation
  private val stableJson = Json(configuration = Stable, context = IsoJavaTimeSerialModule)

  @Serializable
  data class Bean(
    @ContextualSerialization
    val p1: LocalDate,
    @ContextualSerialization
    val p2: LocalDateTime,
    @ContextualSerialization
    val p3: LocalTime,
    @ContextualSerialization
    val p4: MonthDay,
    @ContextualSerialization
    val p5: Month,
    @ContextualSerialization
    val p6: YearMonth,
    @ContextualSerialization
    val p7: Year
  )

  @Test
  fun test() {
    val str = """{
      "p1": "2019-12-01",
      "p2": "2019-12-01T10:20:30",
      "p3": "10:20:30",
      "p4": "12-01",
      "p5": "12",
      "p6": "2019-12",
      "p7": "2019"
    }"""
    val b = stableJson.parse(Bean.serializer(), str)
    assertThat(b.p1).isEqualTo(LocalDate.of(2019, 12, 1))
    assertThat(b.p2).isEqualTo(LocalDateTime.of(2019, 12, 1, 10, 20, 30))
    assertThat(b.p3).isEqualTo(LocalTime.of(10, 20, 30))
    assertThat(b.p4).isEqualTo(MonthDay.of(12, 1))
    assertThat(b.p5).isEqualTo(Month.of(12))
    assertThat(b.p6).isEqualTo(YearMonth.of(2019, 12))
    assertThat(b.p7).isEqualTo(Year.of(2019))
  }
}