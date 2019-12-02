package tech.simter.kotlin.serialization

import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
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
  fun `Failed to implement KSerializer with inheritance`() {
    val str = """{
      "p": "2019-12-02"
    }"""
    val b = stableJson.parse(Bean.serializer(), str)
    assertThat(b.p).isEqualTo(LocalDate.of(2019, 12, 1))
  }
}

@Serializer(forClass = LocalDate::class)
object IsoLocalDateSerializer : LocalDateSerializer(DateTimeFormatter.ISO_DATE)

/**
 * TODO : Failed to implement KSerializer with inheritance
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