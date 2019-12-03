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
    coords.forEach { coord ->
      when (coord.dir) {
        'R' -> {
          (0 until coord.distance).forEach { _ ->
            currX++
            wires[currX to currY] = (wires[currX to currY] ?: 0) + 1
          }
        }
        'L' -> {
          (0 until coord.distance).forEach { _ ->
            currX--
            wires[currX to currY] = (wires[currX to currY] ?: 0) + 1
          }
        }
        'D' -> {
          (0 until coord.distance).forEach { _ ->
            currY--
            wires[currX to currY] = (wires[currX to currY] ?: 0) + 1
          }
        }
        'U' -> {
          (0 until coord.distance).forEach { _ ->
            currY++
            wires[currX to currY] = (wires[currX to currY] ?: 0) + 1
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

  fun solvePartII(input: List<String>): String {
    TODO("not implemented")
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
//  println("Part II: ${day.solvePartII(formattedInput)}")
}