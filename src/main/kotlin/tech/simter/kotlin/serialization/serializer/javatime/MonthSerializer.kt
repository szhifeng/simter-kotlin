package tech.simter.kotlin.serialization.serializer.javatime

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.Month

/**
 * A [KSerializer] between [Month] and digit number from 1 to 12.
 *
 * @author RJ
 */
object MonthSerializer : KSerializer<Month> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName(Month::class.qualifiedName!!)

  override fun deserialize(decoder: Decoder): Month {
    return Month.of(decoder.decodeInt())
  }

  override fun serialize(encoder: Encoder, obj: Month) {
    encoder.encodeInt(obj.value)
  }
}