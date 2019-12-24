package tech.simter.kotlin.serialization.serializer.javatime.iso.nullable

import kotlinx.serialization.Decoder
import kotlinx.serialization.KSerializer
import tech.simter.kotlin.serialization.serializer.javatime.AbstractNullableJavaTimeSerializer
import java.time.MonthDay
import java.time.format.DateTimeFormatter.ofPattern

/**
 * A [KSerializer] between [MonthDay] and string value with format 'MM-dd'.
 *
 * @author RJ
 */
object IsoMonthDaySerializer : AbstractNullableJavaTimeSerializer<MonthDay?>(ofPattern("MM-dd")) {
  override fun deserialize(decoder: Decoder): MonthDay? {
    val v = decoder.decodeString()
    return if (v.isEmpty()) null else MonthDay.parse(v, formatter)
  }
}