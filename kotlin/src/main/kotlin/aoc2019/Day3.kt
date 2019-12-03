package aoc2019

import kotlin.math.abs

class Day3 {
  data class Coord(
      val dir: Char,
      val distance: Int
  )

  fun solvePartIForInput(input: String): Int? {
    val formattedInput = input
        .lines()
        .filter(String::isNotEmpty)
        .map { it.split(",").filter(String::isNotEmpty) }
    return solvePartI(formattedInput)
  }

  private fun wires(input: List<String>): Map<Pair<Int, Int>, Int> {
    val wires = mutableMapOf<Pair<Int, Int>, Int>()
    var currX = 0
    var currY = 0
    val coords = input.map { Coord(it[0], it.substring(1).toInt()) }
    var length = 0
    coords.forEach { coord ->
      when (coord.dir) {
        'R' -> {
          (0 until coord.distance).forEach { _ ->
            currX++
            wires[currX to currY] = ++length
          }
        }
        'L' -> {
          (0 until coord.distance).forEach { _ ->
            currX--
            wires[currX to currY] = ++length
          }
        }
        'D' -> {
          (0 until coord.distance).forEach { _ ->
            currY--
            wires[currX to currY] = ++length
          }
        }
        'U' -> {
          (0 until coord.distance).forEach { _ ->
            currY++
            wires[currX to currY] = ++length
          }
        }
        else -> throw IllegalArgumentException("Invalid direction ${coord.dir}")
      }
    }
    return wires
  }

  private fun solvePartI(input: List<List<String>>): Int {
    val wire1 = wires(input[0])
    val wire2 = wires(input[1])
    var minDistance = Int.MAX_VALUE
    (wire1.keys.intersect(wire2.keys)).toSet().forEach { (x, y) ->
      if (x != 0 && y != 0) {
        minDistance = minOf(minDistance, abs(x) + abs(y))
      }
    }
    return minDistance
  }

  private fun solvePartII(input: List<List<String>>): Int {
    val wire1 = wires(input[0])
    val wire2 = wires(input[1])
    var minLength = Int.MAX_VALUE
    (wire1.keys.intersect(wire2.keys)).toSet().forEach { k ->
      if (k != 0 to 0) {
        minLength = minOf(minLength, wire1[k]!! + wire2[k]!!)
      }
    }
    return minLength
  }

  fun solvePartIIForInput(input: String): Int {
    val formattedInput = input
        .lines()
        .filter(String::isNotEmpty)
        .map { it.split(",").filter(String::isNotEmpty) }
    return solvePartII(formattedInput)
  }
}

fun main(@Suppress("UnusedMainParameter") args: Array<String>) {
  val day = Day3()
  val input = day.javaClass
      .classLoader
      .getResourceAsStream("2019/day3.txt")!!
      .bufferedReader()
      .readText()
  println("Part I: ${day.solvePartIForInput(input)}")
  println("Part II: ${day.solvePartIIForInput(input)}")
}