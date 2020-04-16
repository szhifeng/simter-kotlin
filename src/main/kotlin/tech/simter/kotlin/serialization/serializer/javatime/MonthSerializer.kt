package tech.simter.kotlin.serialization.serializer.javatime

import kotlinx.serialization.*
import java.time.Month

/**
 * A [KSerializer] between [Month] and digit number from 1 to 12.
 *
 * @author RJ
 */
object MonthSerializer : KSerializer<Month> {
  override val descriptor: SerialDescriptor = PrimitiveDescriptor(
    serialName = Month::class.qualifiedName!!,
    kind = PrimitiveKind.STRING
  )

  override fun deserialize(decoder: Decoder): Month {
    return Month.of(decoder.decodeInt())
  }

  override fun serialize(encoder: Encoder, value: Month) {
    encoder.encodeInt(value.value)
  }
}