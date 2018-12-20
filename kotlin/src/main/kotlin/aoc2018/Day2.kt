package aoc2018

class Day2 {
  fun solve() {
    var repeatingTwice = 0
    var repeatingThrice = 0
    val allIds = javaClass.classLoader.getResourceAsStream("day2.txt")
        .bufferedReader()
        .readLines()
    allIds.forEach {
      val (twice, thrice) = countChars(it)
      repeatingTwice += twice
      repeatingThrice += thrice
    }
    println("Part I: ${repeatingTwice * repeatingThrice}")
    val charsByPosition = mutableMapOf<Int, MutableSet<Char>>()
    allIds.forEach { id ->
      id.toCharArray().forEachIndexed { i, c ->
        charsByPosition[i] = charsByPosition[i]?.also { it.add(c) } ?: mutableSetOf(c)
      }
    }
    for (id1 in allIds) {
      for (id2 in allIds) {
        if (isMatch(id1, id2)) {
          println("part II: $id1, $id2")
          return
        }
      }
    }
  }

  private fun isMatch(a: String, b: String): Boolean {
    var distance = 0
    a.forEachIndexed { i, _ ->
      if (a[i] != b[i]) {
        if (distance == 0) {
          distance++
        } else {
          return false
        }
      }
    }
    return distance == 1
  }

  private fun countChars(input: String): Pair<Int, Int> {
    val counts = mutableMapOf<Char, Int>()
    input.toCharArray().forEach { c ->
      counts[c] = counts[c]?.let { it + 1 } ?: 1
    }
    val hasTwice = if (counts.filterValues { it == 2 }.any()) 1 else 0
    val hasThrice = if (counts.filterValues { it == 3 }.any()) 1 else 0
    return hasTwice to hasThrice
  }
}

fun main(args: Array<String>) {
  Day2().solve()
}
