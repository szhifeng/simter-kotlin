package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * A [LocalTime] [KSerializer] with ISO format 'HH:mm:ss.SSS'.
 *
 * @author RJ
 */
@Serializer(forClass = LocalTime::class)
object IsoLocalTimeSerializer : KSerializer<LocalTime> {
  private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.LocalTime")

  override fun deserialize(decoder: Decoder): LocalTime {
    return LocalTime.parse(decoder.decodeString(), formatter)
  }

  override fun serialize(encoder: Encoder, obj: LocalTime) {
    encoder.encodeString(obj.format(formatter))
  }
}