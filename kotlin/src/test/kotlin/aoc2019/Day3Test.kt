package aoc2019

import org.junit.Test
import kotlin.test.assertEquals

class Day3Test {
  class Day10Test {
    @Test fun `sample input 1`() {
      assertEquals(6, Day3().solvePartIForInput("""
        R8,U5,L5,D3
        U7,R6,D4,L4
      """.trimIndent()))
      assertEquals(30, Day3().solvePartIIForInput("""
        R8,U5,L5,D3
        U7,R6,D4,L4
      """.trimIndent()))
    }

    @Test fun `sample input 2`() {
      assertEquals(159, Day3().solvePartIForInput("""
        R75,D30,R83,U83,L12,D49,R71,U7,L72
        U62,R66,U55,R34,D71,R55,D58,R83
      """.trimIndent()))
      assertEquals(610, Day3().solvePartIIForInput("""
        R75,D30,R83,U83,L12,D49,R71,U7,L72
        U62,R66,U55,R34,D71,R55,D58,R83
      """.trimIndent()))
    }

    @Test fun `sample input 3`() {
      assertEquals(135, Day3().solvePartIForInput("""
        R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
        U98,R91,D20,R16,D67,R40,U7,R15,U6,R7
      """.trimIndent()))
      assertEquals(410, Day3().solvePartIIForInput("""
        R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
        U98,R91,D20,R16,D67,R40,U7,R15,U6,R7
      """.trimIndent()))
    }
  }
}