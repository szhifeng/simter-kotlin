package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.MonthDay
import java.time.format.DateTimeFormatter

/**
 * A [MonthDay] [KSerializer] with string format 'MM-dd'.
 *
 * @author RJ
 */
@Serializer(forClass = MonthDay::class)
object IsoMonthDaySerializer : KSerializer<MonthDay> {
  private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd")
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.MonthDay")

  override fun deserialize(decoder: Decoder): MonthDay {
    return MonthDay.parse(decoder.decodeString(), formatter)
  }

  override fun serialize(encoder: Encoder, obj: MonthDay) {
    encoder.encodeString(obj.format(formatter))
  }
}