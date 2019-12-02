// See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/custom_serializers.md#useserializers-annotation
// A file-level annotation `UseSerializers` instructs all properties of the Serializer's type
// in all classes in this file would be serialized with these Serializers.
@file:UseSerializers(
  IsoLocalDateSerializer::class,
  IsoLocalDateTimeSerializer::class,
  IsoLocalTimeSerializer::class,
  IsoMonthDaySerializer::class,
  IsoMonthSerializer::class,
  IsoYearMonthSerializer::class,
  IsoYearSerializer::class
)

package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.*

/**
 * Test file-level annotation [UseSerializers].
 *
 * @author RJ
 */
class FileLevelUseSerializersTest {
  private val stableJson = Json(Stable)

  @Serializable
  data class Bean(
    val p1: LocalDate,
    val p2: LocalDateTime,
    val p3: LocalTime,
    val p4: MonthDay,
    val p5: Month,
    val p6: YearMonth,
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