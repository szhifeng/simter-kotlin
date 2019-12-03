package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A [LocalDateTime] [KSerializer] with common format 'yyyy-MM-dd HH:mm:ss'.
 *
 * @author RJ
 */
@Serializer(forClass = LocalDateTime::class)
object CommonLocalDateTimeSerializer : KSerializer<LocalDateTime> {
  private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.LocalDateTime")

  override fun deserialize(decoder: Decoder): LocalDateTime {
    return LocalDateTime.parse(decoder.decodeString(), formatter)
  }

  override fun serialize(encoder: Encoder, obj: LocalDateTime) {
    encoder.encodeString(obj.format(formatter))
  }
}