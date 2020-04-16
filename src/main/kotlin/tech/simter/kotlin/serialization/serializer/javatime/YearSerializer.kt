package tech.simter.kotlin.serialization.serializer.javatime

import kotlinx.serialization.*
import java.time.Year

/**
 * A [KSerializer] between [Year] and 4 digit number.
 *
 * @author RJ
 */
object YearSerializer : KSerializer<Year> {
  override val descriptor: SerialDescriptor = PrimitiveDescriptor(
    serialName = Year::class.qualifiedName!!,
    kind = PrimitiveKind.STRING
  )

  override fun deserialize(decoder: Decoder): Year {
    return Year.of(decoder.decodeInt())
  }

  override fun serialize(encoder: Encoder, value: Year) {
    encoder.encodeInt(value.value)
  }
}