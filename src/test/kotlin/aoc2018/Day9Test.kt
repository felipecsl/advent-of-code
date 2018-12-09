package aoc2018

import org.junit.Assert.*
import org.junit.Test

class Day9Test {
  @Test fun `test sample input`() {
    val day9 = Day9()
    assertEquals(32, day9.solve(9, 25))
    assertEquals(8317, day9.solve(10, 1618))
    assertEquals(146373, day9.solve(13, 7999))
    assertEquals(2764, day9.solve(17, 1104))
    assertEquals(54718, day9.solve(21, 6111))
    assertEquals(37305, day9.solve(30, 5807))
  }
}