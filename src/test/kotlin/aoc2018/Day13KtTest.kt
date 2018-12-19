package aoc2018

import org.junit.Assert.assertEquals
import org.junit.Test

class Day13KtTest {
  @Test fun `test sample input`() {
    val input = """
/->-\
|   |  /----\
| /-+--+-\  |
| | |  | v  |
\-+-/  \-+--/
  \------/
""".trimIndent()
    assertEquals("(7, 3)", Day13().solve(input))
  }
}