package tech.simter.kotlin.serialization.serializer.math.nullable

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.math.BigDecimal

/**
 * A [KSerializer] between [BigDecimal] and digit number.
 *
 * @author RJ
 */
object BigDecimalSerializer : KSerializer<BigDecimal?> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName(BigDecimal::class.qualifiedName!!)

  override fun deserialize(decoder: Decoder): BigDecimal? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else BigDecimal(v)
  }

  override fun serialize(encoder: Encoder, obj: BigDecimal?) {
    if (obj == null) encoder.encodeNull()
    else encoder.encodeDouble(obj.toDouble())
  }
}