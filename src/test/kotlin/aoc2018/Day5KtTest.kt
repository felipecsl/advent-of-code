package aoc2018

import org.junit.Assert.*
import org.junit.Test

class Day5KtTest {
  private val day5 = Day5()

  @Test fun `test isMatch`() {
    assertTrue(isMatch('a', 'A'))
    assertTrue(isMatch('B', 'b'))
    assertTrue(isMatch('C', 'c'))
    assertFalse(isMatch('c', 'D'))
    assertFalse(isMatch('c', 'c'))
    assertFalse(isMatch('C', 'C'))
    assertFalse(isMatch(null, null))
  }

  @Test fun `sample input`() {
    assertEquals("", day5.reactV1("aA"))
    assertEquals("abc", day5.reactV1("abc"))
    assertEquals("", day5.reactV1("abBA"))
    assertEquals("aabAAB", day5.reactV1("aabAAB"))
    assertEquals("dabCBAcaDA", day5.reactV1("dabAcCaCBAcCcaDA"))
    assertEquals("Oi", day5.reactV1("kKpPcCZQqzyYvVxXVfYLlyFiIvOcCTtDdi"))
  }

  @Test fun `sample input v2`() {
    assertEquals("", day5.reactV2("aA"))
    assertEquals("abc", day5.reactV2("abc"))
    assertEquals("", day5.reactV2("abBA"))
    assertEquals("aabAAB", day5.reactV2("aabAAB"))
    assertEquals("dabCBAcaDA", day5.reactV2("dabAcCaCBAcCcaDA"))
    assertEquals("Oi", day5.reactV2("kKpPcCZQqzyYvVxXVfYLlyFiIvOcCTtDdi"))
  }
}