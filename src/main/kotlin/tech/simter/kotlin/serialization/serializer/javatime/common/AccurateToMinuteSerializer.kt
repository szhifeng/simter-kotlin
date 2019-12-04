package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
import java.time.temporal.Temporal

/**
 * A [KSerializer] just for serialize javatime to format 'yyyy-MM-dd HH:mm' or 'HH:mm'.
 *
 * - Support [LocalDateTime], [OffsetDateTime], [ZonedDateTime], [LocalTime], [OffsetTime].
 * - Not support deserialize。
 *
 * @author RJ
 */
object AccurateToMinuteSerializer : KSerializer<Temporal> {
  private val dateTimeFormatter: DateTimeFormatter = ofPattern("yyyy-MM-dd HH:mm")
  private val timeFormatter: DateTimeFormatter = ofPattern("HH:mm")
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.temporal.Temporal")

  override fun serialize(encoder: Encoder, obj: Temporal) {
    val formatter = when (obj::class) {
      LocalDateTime::class -> dateTimeFormatter
      OffsetDateTime::class -> dateTimeFormatter
      ZonedDateTime::class -> dateTimeFormatter
      LocalTime::class -> timeFormatter
      OffsetTime::class -> timeFormatter
      else -> throw UnsupportedOperationException("Not support serialize type ${obj.javaClass.name}")
    }
    encoder.encodeString(formatter.format(obj))
  }

  override fun deserialize(decoder: Decoder): Temporal {
    throw UnsupportedOperationException("AccurateToMinuteSerializer not support deserialize")
  }
}