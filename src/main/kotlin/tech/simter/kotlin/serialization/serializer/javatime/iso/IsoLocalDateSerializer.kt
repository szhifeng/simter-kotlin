package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * A [LocalDate] [KSerializer] with ISO format 'yyyy-MM-dd'.
 *
 * @author RJ
 */
@Serializer(forClass = LocalDate::class)
object IsoLocalDateSerializer : KSerializer<LocalDate> {
  private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.LocalDate")

  override fun deserialize(decoder: Decoder): LocalDate {
    return LocalDate.parse(decoder.decodeString(), formatter)
  }

  override fun serialize(encoder: Encoder, obj: LocalDate) {
    encoder.encodeString(obj.format(formatter))
  }
}