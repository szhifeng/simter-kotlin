package tech.simter.kotlin.serialization.serializer.javatime.iso

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.Year

/**
 * A [Year] [KSerializer] with string format 'yyyy'.
 *
 * @author RJ
 */
@Serializer(forClass = Year::class)
object IsoYearSerializer : KSerializer<Year> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.Year")

  override fun deserialize(decoder: Decoder): Year {
    return Year.of(decoder.decodeInt())
  }

  override fun serialize(encoder: Encoder, obj: Year) {
    encoder.encodeString(obj.value.toString())
  }
}