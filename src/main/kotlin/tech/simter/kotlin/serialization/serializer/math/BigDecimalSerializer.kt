package tech.simter.kotlin.serialization.serializer.math

import kotlinx.serialization.*
import java.math.BigDecimal

/**
 * A [KSerializer] between [BigDecimal] and digit number.
 *
 * @author RJ
 */
object BigDecimalSerializer : KSerializer<BigDecimal> {
  override val descriptor: SerialDescriptor = PrimitiveDescriptor(
    serialName = BigDecimal::class.qualifiedName!!,
    kind = PrimitiveKind.STRING
  )

  override fun deserialize(decoder: Decoder): BigDecimal {
    return BigDecimal(decoder.decodeString())
  }

  override fun serialize(encoder: Encoder, value: BigDecimal) {
    encoder.encodeDouble(value.toDouble())
  }
}