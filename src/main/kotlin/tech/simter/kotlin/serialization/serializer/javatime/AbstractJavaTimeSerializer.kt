package tech.simter.kotlin.serialization.serializer.javatime

import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.withName
import java.lang.reflect.ParameterizedType
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

/**
 * A abstract [TemporalAccessor] [KSerializer].
 *
 * For all JavaTime Type Conversion between string with special [formatter].
 *
 * @author RJ
 */
abstract class AbstractJavaTimeSerializer<T : TemporalAccessor>(val formatter: DateTimeFormatter) : KSerializer<T> {
  override val descriptor: SerialDescriptor = StringDescriptor.withName(
    @Suppress("UNCHECKED_CAST")
    ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>).name
  )

  override fun serialize(encoder: Encoder, obj: T) {
    encoder.encodeString(formatter.format(obj))
  }
}