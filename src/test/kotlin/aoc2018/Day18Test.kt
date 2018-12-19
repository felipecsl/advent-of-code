package aoc2018

import org.junit.Test

import org.junit.Assert.*

class Day18Test {
  @Test fun solve() {
    val input = """
      .#.#...|#.
      .....#|##|
      .|..|...#.
      ..|#.....#
      #.#|||#|#|
      ...#.||...
      .|....|...
      ||...#|.#|
      |.||||..|.
      ...#.|..|.
      """.trimIndent()
    assertEquals(1147, Day18().solve(input))
  }
}