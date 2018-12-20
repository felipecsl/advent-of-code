package aoc2018

data class Point(val coords: Coords, val velX: Long, val velY: Long)

class Day10 {
  private lateinit var points: Map<Coords, Point>

  fun solve() {
    val input = javaClass.classLoader.getResourceAsStream("day10.txt")
        .bufferedReader()
        .readText()
        .trim()
    solveForInput(input)
  }

  fun sizeOf(points: Collection<Point>): Long {
    val minX = points.map { it.coords.x }.min()!!
    val maxX = points.map { it.coords.x }.max()!!
    val minY = points.map { it.coords.y }.min()!!
    val maxY = points.map { it.coords.y }.max()!!
    return (maxX - minX) * (maxY - minY)
  }

  fun solveForInput(input: String): String {
    points = input.split("\n").map(::parsePoint).associate { it.coords to it }
    for (secs in 0..Int.MAX_VALUE) {
      val newPoints = points.mapValues { (_, point) ->
        point.copy(coords = point.coords.copy(
            x = point.coords.x + (point.velX * secs),
            y = point.coords.y + (point.velY * secs)
        ))
      }.mapKeys { (_, v) -> v.coords }
      val newSize = sizeOf(newPoints.values)
      // magic number 549 was found by printing newSize to console on every iteration and finding
      // the smallest number in the sequence
      if (newSize == 549L) {
        return printMessage(newPoints)
      }
    }
    return ""
  }

  private fun printMessage(points: Map<Coords, Point>): String {
    val minX = points.values.map { it.coords.x }.min()!!
    val maxX = points.values.map { it.coords.x }.max()!!
    val minY = points.values.map { it.coords.y }.min()!!
    val maxY = points.values.map { it.coords.y }.max()!!
    (minY..maxY).forEach { y ->
      (minX..maxX).forEach { x ->
        val point = points[Coords(x, y)]
        if (point != null) {
          print("#")
        } else {
          print(".")
        }
      }
      println()
    }
    return ""
  }

  private fun parsePoint(it: String): Point {
    val matcher = PATTERN.matcher(it)
    val matches = LongArray(4)
    var i = 0
    while (matcher.find() && i < 4) {
      matches[i++] = matcher.group(1).toLong()
    }
    return Point(Coords(matches[0], matches[1]), matches[2], matches[3])
  }

  companion object {
    private val PATTERN = "(-*\\d+)".toPattern()
  }
}

fun main(args: Array<String>) {
  println(Day10().solve())
}