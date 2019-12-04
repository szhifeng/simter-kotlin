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

package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.*

/**
 * Test [AccurateToMinuteSerializer]
 *
 * @author RJ
 */
class AccurateToMinuteSerializerTest {
  // See https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/custom_serializers.md#contextualserialization-annotation
  private val json = Json(configuration = Stable, context = CommonJavaTimeSerialModule)

  @Serializable
  data class Bean(
    val dt0: OffsetDateTime, // Use CommonJavaTimeSerialModule
    @Serializable(with = AccurateToMinuteSerializer::class)
    val dt1: OffsetDateTime,
    @Serializable(with = AccurateToMinuteSerializer::class)
    val dt2: ZonedDateTime,
    @Serializable(with = AccurateToMinuteSerializer::class)
    val dt3: LocalDateTime,
    @Serializable(with = AccurateToMinuteSerializer::class)
    val t1: LocalTime,
    @Serializable(with = AccurateToMinuteSerializer::class)
    val t2: OffsetTime
  )

  @Test
  fun test() {
    val t = OffsetDateTime.of(2019, 1, 31, 1, 20, 59, 0, ZoneOffset.ofHours(2))
    val sdt = "2019-01-31 01:20"
    val st = "01:20"
    val str = """{"dt0":"2019-01-31 01:20:59+02:00","dt1":"$sdt","dt2":"$sdt","dt3":"$sdt","t1":"$st","t2":"$st"}"""
    val bean = Bean(
      dt0 = t,
      dt1 = t,
      dt2 = t.toZonedDateTime(),
      dt3 = t.toLocalDateTime(),
      t1 = t.toLocalTime(),
      t2 = t.toOffsetTime()
    )
    assertThat(json.stringify(Bean.serializer(), bean)).isEqualTo(str)
  }
}