package aoc2018

import org.junit.Assert.assertEquals
import org.junit.Test

class Day8Test {
  @Test fun `sample data`() {
    val day8 = Day8()
    assertEquals(138, day8.partI("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"))
    assertEquals(66, day8.partII())
  }
}