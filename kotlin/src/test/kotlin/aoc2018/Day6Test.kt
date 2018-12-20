package aoc2018

import org.junit.Assert.assertEquals
import org.junit.Test

class Day6Test {
  @Test fun `sample input`() {
    assertEquals(17 to 16, Day6().solveForInput("""
    1, 1
    1, 6
    8, 3
    3, 4
    5, 5
    8, 9
    """.trimIndent().split("\n"), 32))
  }

  @Test fun `closest distance conflict`() {
    val coordA = Coord(3, 4, 1)
    val coordB = Coord(1, 6, 2)
    val day6 = Day6()
    assertEquals(2, coordA.distanceTo(1, 4))
    assertEquals(2, coordB.distanceTo(1, 4))
    assertEquals(null, day6.findClosestCoordTo(1, 4, listOf(coordA, coordB)))
  }
}