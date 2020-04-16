package tech.simter.kotlin.serialization

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration.Companion.Stable
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @author RJ
 */
class KSerializerInheritanceTest {
  private val stableJson = Json(Stable)

  @Serializable
  data class Bean(
    @Serializable(with = IsoLocalDateSerializer::class)
    val p: LocalDate
  )

  @Test
  fun test() {
    val json = """{"p":"2019-12-01"}"""
    val bean = Bean(p = LocalDate.of(2019, 12, 1))

    // deserialize
    assertThat(stableJson.parse(Bean.serializer(), json)).isEqualTo(bean)

    // serialize
    assertThat(stableJson.stringify(Bean.serializer(), bean)).isEqualTo(json)
  }
}

//@Serializer(forClass = LocalDate::class)
// - add this annotation would raise error "kotlinx.serialization.json.JsonDecodingException: Invalid JSON at 13: Expected '{, kind: CLASS'"
//   See https://github.com/Kotlin/kotlinx.serialization/issues/619
object IsoLocalDateSerializer : LocalDateSerializer(DateTimeFormatter.ISO_DATE)

open class LocalDateSerializer(private val formatter: DateTimeFormatter) : KSerializer<LocalDate> {
  override val descriptor: SerialDescriptor = PrimitiveDescriptor(
    serialName = "java.time.LocalDate",
    kind = PrimitiveKind.STRING
  )

  override fun deserialize(decoder: Decoder): LocalDate {
    return LocalDate.parse(decoder.decodeString(), formatter)
  }

  override fun serialize(encoder: Encoder, value: LocalDate) {
    encoder.encodeString(value.format(formatter))
  }
}