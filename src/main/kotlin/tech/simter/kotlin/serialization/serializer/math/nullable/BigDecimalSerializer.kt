package tech.simter.kotlin.serialization.serializer.math.nullable

import kotlinx.serialization.*
import java.math.BigDecimal

/**
 * A [KSerializer] between [BigDecimal] and digit number.
 *
 * @author RJ
 */
object BigDecimalSerializer : KSerializer<BigDecimal?> {
  override val descriptor: SerialDescriptor = PrimitiveDescriptor(
    serialName = BigDecimal::class.qualifiedName!!,
    kind = PrimitiveKind.STRING
  )

  override fun deserialize(decoder: Decoder): BigDecimal? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else BigDecimal(v)
  }

  override fun serialize(encoder: Encoder, value: BigDecimal?) {
    if (value == null) encoder.encodeNull()
    else encoder.encodeDouble(value.toDouble())
  }
}