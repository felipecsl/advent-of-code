package aoc2018

class Day19 {
  fun solve(input: String): String {
    TODO()
  }
}

fun main(args: Array<String>) {
  val day = Day19()
  val input = day.javaClass
      .classLoader
      .getResourceAsStream("day19.txt")
      .bufferedReader()
      .readText()
  println("Part I: ${day.solve(input)}")
}