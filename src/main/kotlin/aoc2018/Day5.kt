package aoc2018

/**
--- Day 5: Alchemical Reduction ---
You've managed to sneak in to the prototype suit manufacturing lab. The Elves are making decent progress, but are still struggling with the suit's size reduction capabilities.

While the very latest in 1518 alchemical technology might have solved their problem eventually, you can do better. You scan the chemical composition of the suit's material and discover that it is formed by extremely long polymers (one of which is available as your puzzle input).

The polymer is formed by smaller units which, when triggered, react with each other such that two adjacent units of the same type and opposite polarity are destroyed. Units' types are represented by letters; units' polarity is represented by capitalization. For instance, r and R are units with the same type but opposite polarity, whereas r and s are entirely different types and do not react.

For example:

In aA, a and A react, leaving nothing behind.
In abBA, bB destroys itself, leaving aA. As above, this then destroys itself, leaving nothing.
In abAB, no two adjacent units are of the same type, and so nothing happens.
In aabAAB, even though aa and AA are of the same type, their polarities match, and so nothing happens.
Now, consider a larger example, dabAcCaCBAcCcaDA:

dabAc CaCBAcCcaDA  The first 'cC' is removed.
dabAaCBAcCcaDA    This creates 'Aa', which is removed.
dabCBAcCcaDA      Either 'cC' or 'Cc' are removed (the result is the same).
dabCBAcaDA        No further actions can be taken.
After all possible reactions, the resulting polymer contains 10 units.

How many units remain after fully reacting the polymer you scanned? (Note: in this puzzle and others, the input is large; if you copy/paste your input, make sure you get the whole thing.)
 */

class Day5 {
  fun solve() {
    val rawInput = javaClass.classLoader.getResourceAsStream("day5.txt")
        .bufferedReader()
        .readText()
        .trim()
    val solution1 = reactV1(rawInput)
    val solution2 = reactV2(rawInput)
    println("Part I Solution I: ${solution1.length}")
    println("Part I Solution II: ${solution2.length}")
    val uniqueUnits = rawInput.toLowerCase().toSet()
    val minLength = uniqueUnits.map { unit ->
      reactV2(rawInput.filterNot { it.equals(unit, true) }).length
    }.min()
    println("Part II: $minLength")
  }

  fun reactV2(rawInput: String): String {
    val mutableInput = rawInput.toList().toMutableList()
    while (hasAnyMatches(mutableInput)) {
      var i = 0
      while (i < mutableInput.size - 1) {
        if (i < mutableInput.size - 1 && isMatch(mutableInput[i], mutableInput[i + 1])) {
          mutableInput.removeAt(i)
          mutableInput.removeAt(i)
        }
        i++
      }
    }
    return mutableInput.joinToString("")
  }

  fun reactV1(rawInput: String): String {
    var input = rawInput.toCharArray().toList()
    val originalSize = input.size
    var previousSize = originalSize
    var currentSize = 0
    while (currentSize < previousSize) {
      input = eraseMatches(input)
      if (input.isEmpty()) break
      previousSize = if (currentSize > 0) currentSize else originalSize
      currentSize = input.size
    }
    if (hasAnyMatches(input)) {
      throw IllegalStateException("Got invalid result: ${input.joinToString("")}")
    }
    return input.joinToString("")
  }

  private fun eraseMatches(input: List<Char>): List<Char> {
    val size = input.size
    if (size < 2) {
      // eg: [a] or []
      return input
    } else {
      if (size == 2) {
        // eg: [ab] or [aA]
        return if (isMatch(input[0], input[1])) listOf() else input
      } else {
        // split in half
        val half = size / 2
        var firstHalf = input.take(half)
        var secondHalf = input.drop(half)
        // handle eg.: ([ab], [Bc]), ([a] [Ab]), ([a], [A])
        while (isMatch(firstHalf.lastOrNull(), secondHalf.firstOrNull())) {
          // trim last and first chars
          firstHalf = firstHalf.take(firstHalf.size - 1)
          secondHalf = secondHalf.drop(1)
        }
        return eraseMatches(firstHalf) + eraseMatches(secondHalf)
      }
    }
  }
}

fun hasAnyMatches(input: List<Char>): Boolean {
  input.forEachIndexed { i, c1 ->
    if (i > 0) {
      if (isMatch(input[i - 1], c1)) {
        return true
      }
    }
  }
  return false
}

fun isMatch(a: Char?, b: Char?) =
    if (a == null || b == null) {
      false
    } else {
      if (!a.equals(b, true)) {
        false
      } else {
        // a and b are the same char, check casings
        if (a.isLowerCase() && b.isUpperCase()) {
          true
        } else a.isUpperCase() && b.isLowerCase()
      }
    }

fun main(args: Array<String>) {
  Day5().solve()
}