package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A [LocalDateTime] [KSerializer] with ISO format 'yyyy-MM-ddTHH:mm:ss.SSS'.
 *
 * @author RJ
 */
@Serializer(forClass = LocalDateTime::class)
object IsoLocalDateTimeSerializer : KSerializer<LocalDateTime> {
  private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.LocalDateTime")

  override fun deserialize(decoder: Decoder): LocalDateTime {
    return LocalDateTime.parse(decoder.decodeString(), formatter)
  }

  override fun serialize(encoder: Encoder, obj: LocalDateTime) {
    encoder.encodeString(obj.format(formatter))
  }
}