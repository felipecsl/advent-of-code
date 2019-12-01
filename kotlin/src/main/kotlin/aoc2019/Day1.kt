package aoc2019

import kotlin.math.floor
import kotlin.math.max

class Day1 {
  fun solvePartI(input: List<Int>) =
      input.sumBy { calcFuel(it) }

  fun solvePartII(input: List<Int>): Int {
    return input.sumBy { recursivelyCalcFuel(it) }
  }

  private fun calcFuel(mass: Int) =
      max(0, floor(mass / 3.0).toInt() - 2)

  private fun recursivelyCalcFuel(mass: Int): Int {
    return if (mass == 0) {
      0
    } else {
      val fuel = calcFuel(mass)
      fuel + recursivelyCalcFuel(fuel)
    }
  }
}

fun main(@Suppress("UnusedMainParameter") args: Array<String>) {
  val day = Day1()
  val input = day.javaClass
      .classLoader
      .getResourceAsStream("2019/day1.txt")!!
      .bufferedReader()
      .readText()
  val formattedInput = input.lines()
      .filter(String::isNotEmpty)
      .map(String::toInt)
  println("Part I: ${day.solvePartI(formattedInput)}")
  println("Part II: ${day.solvePartII(formattedInput)}")
}