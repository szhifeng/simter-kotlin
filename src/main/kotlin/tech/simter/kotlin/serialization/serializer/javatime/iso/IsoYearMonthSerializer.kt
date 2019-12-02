package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * A [YearMonth] [KSerializer] with string format 'yyyy-MM'.
 *
 * @author RJ
 */
@Serializer(forClass = YearMonth::class)
object IsoYearMonthSerializer : KSerializer<YearMonth> {
  private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM")
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.YearMonth")

  override fun deserialize(decoder: Decoder): YearMonth {
    return YearMonth.parse(decoder.decodeString(), formatter)
  }

  override fun serialize(encoder: Encoder, obj: YearMonth) {
    encoder.encodeString(obj.format(formatter))
  }
}