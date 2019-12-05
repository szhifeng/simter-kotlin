package tech.simter.kotlin.serialization.serializer.javatime

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.Year

/**
 * A [KSerializer] between [Year] and 4 digit number.
 *
 * @author RJ
 */
object YearSerializer : KSerializer<Year> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName(Year::class.qualifiedName!!)

  override fun deserialize(decoder: Decoder): Year {
    return Year.of(decoder.decodeInt())
  }

  override fun serialize(encoder: Encoder, obj: Year) {
    encoder.encodeInt(obj.value)
  }
}