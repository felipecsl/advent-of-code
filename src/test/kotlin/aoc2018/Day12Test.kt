package aoc2018

import org.junit.Test

import org.junit.Assert.*

class Day12Test {
  @Test fun solve() {
    val day12 = Day12()
    assertEquals(325, day12.solveForInput("""
      initial state: #..#.#..##......###...###

      ...## => #
      ..#.. => #
      .#... => #
      .#.#. => #
      .#.## => #
      .##.. => #
      .#### => #
      #.#.# => #
      #.### => #
      ##.#. => #
      ##.## => #
      ###.. => #
      ###.# => #
      ####. => #
    """.trimIndent()))
  }
}