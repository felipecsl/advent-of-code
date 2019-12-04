package aoc2019

import kotlin.streams.toList

class Day4 {
  fun solvePartI(min: Int, max: Int): Int {
    return (min..max).map(Int::toString).count {
      // It is a six-digit number.
      if (it.length != 6) false
      // Going from left to right, the digits never decrease
      else if (!isNeverDecreasing(it)) false
      else hasTwoEqualAdjacentDigits(it)
    }
  }

  fun solvePartII(min: Int, max: Int): Int {
    return (min..max).map(Int::toString).count {
      // It is a six-digit number.
      if (it.length != 6) false
      // Going from left to right, the digits never decrease
      else if (!isNeverDecreasing(it)) false
      else hasTwoEqualAdjacentDigitsNoLargerGroup(it)
    }
  }

  fun hasTwoEqualAdjacentDigitsNoLargerGroup(pwd: String): Boolean {
    val chars = pwd.chars().toList()
    var i = 1
    var count = 1
    while (i < chars.size) {
      if (chars[i - 1] == chars[i]) {
        count++
      } else {
        if (count == 2) return true
        count = 1
      }
      i++
    }
    return count == 2
  }

  private fun hasTwoEqualAdjacentDigits(pwd: String): Boolean {
    pwd.forEachIndexed { i, c ->
      if (i > 0 && c == pwd[i - 1]) {
        return true
      }
    }
    return false
  }

  private fun isNeverDecreasing(pwd: String): Boolean {
    pwd.forEachIndexed { i, c ->
      if (i > 0 && c < pwd[i - 1]) {
        return false
      }
    }
    return true
  }
}

fun main(@Suppress("UnusedMainParameter") args: Array<String>) {
  val day = Day4()
  println("Part I: ${day.solvePartI(246540, 787419)}")
  println("Part II: ${day.solvePartII(246540, 787419)}")
}