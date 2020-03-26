package tech.simter.kotlin.serialization.serializer.math

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.math.BigDecimal

/**
 * A [KSerializer] between [BigDecimal] and digit number.
 *
 * @author RJ
 */
object BigDecimalSerializer : KSerializer<BigDecimal> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName(BigDecimal::class.qualifiedName!!)

  override fun deserialize(decoder: Decoder): BigDecimal {
    return BigDecimal(decoder.decodeString())
  }

  override fun serialize(encoder: Encoder, obj: BigDecimal) {
    encoder.encodeDouble(obj.toDouble())
  }
}