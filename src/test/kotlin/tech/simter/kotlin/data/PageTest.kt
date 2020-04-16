package tech.simter.kotlin.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import tech.simter.kotlin.data.Page.Companion.calculateOffset
import tech.simter.kotlin.data.Page.Companion.calculatePageCount
import tech.simter.kotlin.data.Page.Companion.calculatePageNo

/**
 * Test [Page].
 *
 * @author RJ
 */
class PageTest {
  @Test
  fun testCalculatePageNo() {
    assertThat(calculatePageNo(0, 0)).isEqualTo(1)
    assertThat(calculatePageNo(0, -1)).isEqualTo(1)
    assertThat(calculatePageNo(100, 0)).isEqualTo(1)
    assertThat(calculatePageNo(100, -1)).isEqualTo(1)

    assertThat(calculatePageNo(0, 25)).isEqualTo(1)
    assertThat(calculatePageNo(24, 25)).isEqualTo(1)
    assertThat(calculatePageNo(25, 25)).isEqualTo(2)
    assertThat(calculatePageNo(26, 25)).isEqualTo(2)
  }

  @Test
  fun testCalculateOffset() {
    assertThat(calculateOffset(0, 0)).isEqualTo(0)
    assertThat(calculateOffset(1, 0)).isEqualTo(0)
    assertThat(calculateOffset(-1, 0)).isEqualTo(0)
    assertThat(calculateOffset(-10, 0)).isEqualTo(0)

    assertThat(calculateOffset(1, 25)).isEqualTo(0)
    assertThat(calculateOffset(2, 25)).isEqualTo(25)
    assertThat(calculateOffset(3, 25)).isEqualTo(50)
  }

  @Test
  fun testCalculatePageCount() {
    assertThat(calculatePageCount(0, 0)).isEqualTo(0)
    assertThat(calculatePageCount(1, 0)).isEqualTo(0)
    assertThat(calculatePageCount(-1, 0)).isEqualTo(0)

    assertThat(calculatePageCount(0, 1)).isEqualTo(0)
    assertThat(calculatePageCount(1, 1)).isEqualTo(1)
    assertThat(calculatePageCount(2, 1)).isEqualTo(2)

    assertThat(calculatePageCount(0, 25)).isEqualTo(0)
    assertThat(calculatePageCount(1, 25)).isEqualTo(1)
    assertThat(calculatePageCount(24, 25)).isEqualTo(1)
    assertThat(calculatePageCount(25, 25)).isEqualTo(1)
    assertThat(calculatePageCount(26, 25)).isEqualTo(2)
  }

  @Test
  fun testToMap() {
    data class Bean(val name: String)

    val page = Page.of(limit = 25, offset = 0, total = 100, rows = emptyList<Bean>())

    // empty mapper
    assertThat(page.toMap())
      .isEqualTo(mapOf(
        "offset" to page.offset,
        "limit" to page.limit,
        "total" to page.total,
        "pageNo" to page.pageNo,
        "pageCount" to page.pageCount,
        "rows" to page.rows
      ))

    // map limit to pageSize ...
    assertThat(page.toMap(
      "offset" to "start",
      "limit" to "pageSize",
      "total" to "totalCount",
      "pageNo" to "pageNumber",
      "pageCount" to "totalPages",
      "rows" to "content"
    )).isEqualTo(mapOf(
      "start" to page.offset,
      "pageSize" to page.limit,
      "totalCount" to page.total,
      "pageNumber" to page.pageNo,
      "totalPages" to page.pageCount,
      "content" to page.rows
    ))
  }
}