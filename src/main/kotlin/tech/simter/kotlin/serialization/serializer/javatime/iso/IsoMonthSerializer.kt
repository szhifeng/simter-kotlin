package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.Month

/**
 * A [Month] [KSerializer] with string format 'MM'.
 *
 * @author RJ
 */
@Serializer(forClass = Month::class)
object IsoMonthSerializer : KSerializer<Month> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.Month")

  override fun deserialize(decoder: Decoder): Month {
    return Month.of(decoder.decodeInt())
  }

  override fun serialize(encoder: Encoder, obj: Month) {
    encoder.encodeString(if (obj.value > 9) obj.value.toString() else "0${obj.value}")
  }
}