package tech.simter.kotlin.serialization.serializer.javatime

import kotlinx.serialization.*
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
abstract class AbstractNullableJavaTimeSerializer<T : TemporalAccessor?>(val formatter: DateTimeFormatter) : KSerializer<T> {
  override val descriptor: SerialDescriptor = PrimitiveDescriptor(
    serialName = @Suppress("UNCHECKED_CAST")
    ((this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>).name,
    kind = PrimitiveKind.STRING
  )

  override fun serialize(encoder: Encoder, value: T) {
    if (value == null) encoder.encodeNull()
    else encoder.encodeString(formatter.format(value))
  }
}