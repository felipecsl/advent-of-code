package aoc2018

import org.junit.Test

import org.junit.Assert.*

class Day17Test {
  @Test fun solve() {
    val input = """
x=495, y=2..7
y=7, x=495..501
x=501, y=3..7
x=498, y=2..4
x=506, y=1..2
x=498, y=10..13
x=504, y=10..13
y=13, x=498..504
    """.trimIndent()
    assertEquals(57, Day17().solve(input))
  }
}