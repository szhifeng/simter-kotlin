package tech.simter.kotlin.serialization.serializer.javatime

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * A [LocalDate] [KSerializer].
 *
 * TODO : can not use inherit for KSerializer
 *
 * @author RJ
 */
open class LocalDateSerializer(private val formatter: DateTimeFormatter) : KSerializer<LocalDate> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.LocalDate")

  override fun deserialize(decoder: Decoder): LocalDate {
    return LocalDate.parse(decoder.decodeString(), formatter)
  }

  override fun serialize(encoder: Encoder, obj: LocalDate) {
    encoder.encodeString(obj.format(formatter))
  }
}

@Serializer(forClass = LocalDate::class)
object IsoLocalDateSerializerByInherit : LocalDateSerializer(DateTimeFormatter.ISO_DATE)