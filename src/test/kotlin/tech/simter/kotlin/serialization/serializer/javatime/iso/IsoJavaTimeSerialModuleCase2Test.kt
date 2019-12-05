// See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/custom_serializers.md#contextualserialization-annotation
// A file-level annotation `@file:ContextualSerialization(A::class, B::class)`
// instructs compiler plugin to use ContextSerializer everywhere in this file for properties of types A and B.
@file:ContextualSerialization(
  OffsetDateTime::class,
  LocalDateTime::class,
  LocalDate::class,
  LocalTime::class,
  MonthDay::class,
  Month::class,
  YearMonth::class,
  Year::class
)

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
class IsoJavaTimeSerialModuleCase2Test {
  // See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/custom_serializers.md#contextualserialization-annotation
  private val json = Json(configuration = Stable, context = IsoJavaTimeSerialModule)

  @Serializable
  data class Bean(
    val p1: LocalDateTime,
    val p2: LocalDate,
    val p3: LocalTime,
    val p4: MonthDay,
    val p5: Month,
    val p6: YearMonth,
    val p7: Year,
    val p8: OffsetDateTime
  )

  @Test
  fun test() {
    val t = OffsetDateTime.of(2019, 1, 31, 1, 20, 59, 0, ZoneOffset.ofHours(2))
    val str = """{
      "p1": "2019-01-31T01:20:59",
      "p2": "2019-01-31",
      "p3": "01:20:59",
      "p4": "01-31",
      "p5": 1,
      "p6": "2019-01",
      "p7": 2019,
      "p8": "2019-01-31T01:20:59+02:00"
    }""".replace(" ", "")
      .replace("\r\n", "")
      .replace("\r", "")
      .replace("\n", "")
    val bean = Bean(
      p1 = t.toLocalDateTime(),
      p2 = t.toLocalDate(),
      p3 = t.toLocalTime(),
      p4 = MonthDay.from(t),
      p5 = t.month,
      p6 = YearMonth.from(t),
      p7 = Year.from(t),
      p8 = t
    )
    assertThat(json.parse(Bean.serializer(), str)).isEqualTo(bean)
    assertThat(json.stringify(Bean.serializer(), bean)).isEqualTo(str)
  }
}