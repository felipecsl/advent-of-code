package aoc2018

class Day12 {
  fun solve(): Int {
    val input = javaClass.classLoader.getResourceAsStream("day12.txt")
        .bufferedReader()
        .readText()
    return solveForInput(input)
  }

  fun solveForInput(input: String): Int {
    val lines = input.split("\n")
    var state = "..." + lines[0].replace("initial state: ", "") + ".............."
    val mutableState = state.toCharArray().toMutableList()
    val patterns = lines.drop(2).map {
      val (pattern, result) = it.split(" => ")
      pattern to result
    }
    println("0: $state")
    (1 .. 20).forEach { generation ->
      if (!state.endsWith(".....")) {
        state = "$state....."
        mutableState += ".....".toCharArray().toMutableList()
      }
      (1 until state.length - 3).forEach { i ->
        val sub = if (i > 1) state.subSequence(i - 2, i + 3) else "." + state.subSequence(i - 1, i + 3)
        mutableState[i] = replaceMatch(patterns, sub)
      }
      state = mutableState.joinToString("")
    }
    // For part II, manually run and output the sum every 100-1000 iterations to see that it quickly
    // stabilizes after running for a bit. Then, manually do the math to find the final sum after
    // 50B generations.
    return findSum(state)
  }

  private fun findSum(state: String): Int {
    var sum = 0
    (0 until state.length).forEach { i ->
      sum += if (state[i] == '#') i - 3 else 0
    }
    return sum
  }

  private fun replaceMatch(patterns: List<Pair<String, String>>, candidate: CharSequence): Char {
    val match = patterns.firstOrNull { (pattern, _) -> pattern == candidate }
    return match?.second?.firstOrNull() ?: '.'
  }
}

fun main(args: Array<String>) {
  println(Day12().solve())
}