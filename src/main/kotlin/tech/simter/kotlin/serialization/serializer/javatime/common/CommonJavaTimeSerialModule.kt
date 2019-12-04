package tech.simter.kotlin.serialization.serializer.javatime.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerialModule
import kotlinx.serialization.modules.serializersModuleOf
import tech.simter.kotlin.serialization.serializer.javatime.MonthSerializer
import tech.simter.kotlin.serialization.serializer.javatime.YearSerializer
import tech.simter.kotlin.serialization.serializer.javatime.iso.IsoLocalDateSerializer
import tech.simter.kotlin.serialization.serializer.javatime.iso.IsoLocalTimeSerializer
import tech.simter.kotlin.serialization.serializer.javatime.iso.IsoMonthDaySerializer
import tech.simter.kotlin.serialization.serializer.javatime.iso.IsoYearMonthSerializer
import java.time.*
import kotlin.reflect.KClass

/**
 * All common format javatime serializers.
 *
 * ## Usage case 1: use `@ContextualSerialization` on a property
 *
 * ```
 * @Serializable
 * data class Bean(
 *   @ContextualSerialization
 *   val p: LocalDateTime
 * )
 *
 * private val json = Json(context = CommonJavaTimeSerialModule)
 * val obj: LocalDateTime = json.parse(Bean.serializer(), """{"p": "2019-12-01 10:20:30"}""")
 * val str: String = json.stringify(Bean.serializer(), LocalDateTime.of(2019, 12, 1, 10, 20, 30))
 * ```
 *
 *
 * ## Usage case 2: use `@file:ContextualSerialization(...)` on a file
 *
 * ```
 * @file:ContextualSerialization(LocalDate::class, ...)
 * package ...
 * import ...
 * class XTest {
 *   private val json = Json(configuration = Stable, context = CommonJavaTimeSerialModule)
 *
 *   @Serializable
 *   data class Bean(
 *     val p: LocalDateTime,
 *     ...
 *   )
 *
 *   @Test
 *   fun test() {
 *     val obj: LocalDateTime = json.parse(Bean.serializer(), """{"p": "2019-12-01 10:20:30"}""")
 *     val str: String = json.stringify(Bean.serializer(), LocalDateTime.of(2019, 12, 1, 10, 20, 30))
 *   }
 * }
 * ```
 */
val CommonJavaTimeSerialModule: SerialModule = serializersModuleOf(mapOf<KClass<*>, KSerializer<*>>(
  LocalDate::class to IsoLocalDateSerializer,
  LocalDateTime::class to CommonLocalDateTimeSerializer,
  LocalTime::class to IsoLocalTimeSerializer,
  MonthDay::class to IsoMonthDaySerializer,
  Month::class to MonthSerializer,
  YearMonth::class to IsoYearMonthSerializer,
  Year::class to YearSerializer
))