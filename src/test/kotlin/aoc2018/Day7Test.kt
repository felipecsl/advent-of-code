package aoc2018

import org.junit.Assert.assertEquals
import org.junit.Test

class Day7Test {
  @Test fun `test sample input`() {
    val day7 = Day7()
    assertEquals("CABDFE", day7.solveForInput("""
      Step C must be finished before step A can begin.
      Step C must be finished before step F can begin.
      Step A must be finished before step B can begin.
      Step A must be finished before step D can begin.
      Step B must be finished before step E can begin.
      Step D must be finished before step E can begin.
      Step F must be finished before step E can begin.
    """.trimIndent().split("\n")))
  }
}