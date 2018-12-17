package aoc2018

data class Coord17(var x: Int, var y: Int)

class Day17 {
  private var clayCoords: MutableSet<Coord17> = mutableSetOf()
  private val water: MutableSet<Coord17> = mutableSetOf()
  private val wet: MutableSet<Coord17> = mutableSetOf()
  private val maxX by lazy { clayCoords.maxBy { it.x }!!.x + 1 }
  private val minX by lazy { clayCoords.minBy { it.x }!!.x - 1 }
  private val minY by lazy { clayCoords.minBy { it.y }!!.y }
  private val maxY by lazy { clayCoords.maxBy { it.y }!!.y }

  fun solve(input: String) {
    input.lines().forEach {
      val (a, b) = it.split(", ")
      if (a.startsWith("x=")) {
        val xRange = parseCoord(a.replace("x=", ""))
        val yRange = parseCoord(b.replace("y=", ""))
        parseClayCoords(xRange, yRange)
      } else {
        val xRange = parseCoord(b.replace("x=", ""))
        val yRange = parseCoord(a.replace("y=", ""))
        parseClayCoords(xRange, yRange)
      }
    }
    dumpWater(500, 0)
//    println(layoutAsString())
    println("Part I: ${countWaterTiles()}")
    println("Part II: ${countWaterOnlyTiles()}")
  }

  private fun parseClayCoords(xRange: IntRange, yRange: IntRange) {
    xRange.forEach { x ->
      yRange.forEach { y ->
        clayCoords.add(Coord17(x, y))
      }
    }
  }

  private fun countWaterTiles(): Int {
    return (minY..maxY).sumBy { y ->
      (minX..maxX).sumBy { x ->
        when {
          hasWaterAt(x, y) -> 1
          isWetAt(x, y) -> 1
          else -> 0
        }
      }
    }
  }

  private fun countWaterOnlyTiles(): Int {
    return (minY..maxY).sumBy { y ->
      (minX..maxX).sumBy { x ->
        when {
          hasWaterAt(x, y) -> 1
          else -> 0
        }
      }
    }
  }

  private fun dumpWater(startX: Int, startY: Int) {
    if (hasClayAt(startX, startY) || startX < minX || startY > maxY) return
    var waterY = startY
    var waterX = startX
    var foundClay = false
    for (currY in startY..maxY) {
      if (hasClayAt(waterX, currY)) {
        waterY = currY - 1
        foundClay = true
        break
      } else {
        wet.add(Coord17(waterX, currY))
      }
    }
    while (waterY > startY) {
      if (!fillWaterLineAt(waterX, waterY)) break
      waterY--
    }
    // only flow water around if clay was found
    if (!foundClay) return
    val initialWaterX = waterX
    waterX++
    if (!isWetAt(waterX, waterY)) {
      // flow right
      while (waterX <= maxX) {
        if (hasClayOrWaterAt(waterX, waterY + 1) && !hasClayAt(waterX, waterY)) {
          wet.add(Coord17(waterX, waterY))
        } else {
          break
        }
        waterX++
      }
      if (!hasClayOrWaterAt(waterX, waterY) && waterX <= maxX) {
        dumpWater(waterX, waterY)
      }
    }
    // flow left
    waterX = initialWaterX - 1
    if (!isWetAt(waterX, waterY)) {
      while (waterX >= minX) {
        if (hasClayOrWaterAt(waterX, waterY + 1) && !hasClayAt(waterX, waterY)) {
          wet.add(Coord17(waterX, waterY))
        } else {
          break
        }
        waterX--
      }
      if (!hasClayOrWaterAt(waterX, waterY) && waterX >= minX) {
        dumpWater(waterX, waterY)
      }
    }
    // handle the case when there is a container inside the other by re-recursing again
    var hadMoreSpaceToFill = false
    while (fillWaterLineAt(waterX, waterY)) {
      hadMoreSpaceToFill = true
      dumpWater(waterX, waterY--)
    }
    if (hadMoreSpaceToFill) dumpWater(waterX, waterY)
  }

  private fun fillWaterLineAt(waterX: Int, currY: Int): Boolean {
    var leftBoundary = -1
    var rightBoundary = -1
    for (x in waterX..maxX) {
      if (hasClayAt(x, currY)) {
        rightBoundary = x - 1
        break
      }
    }
    var x = waterX
    while (x-- > minX) {
      if (hasClayAt(x, currY)) {
        leftBoundary = x + 1
        break
      }
    }
    // check if properly contained
    if (leftBoundary == -1 || rightBoundary == -1) return false
    val xRange = leftBoundary..rightBoundary
    val isContained = xRange.all { x1 -> hasClayOrWaterAt(x1, currY + 1) }
    if (isContained) {
      xRange.forEach { x1 -> water.add(Coord17(x1, currY)) }
    }
    return isContained
  }

  private fun hasClayOrWaterAt(x: Int, y: Int) =
      hasWaterAt(x, y) || hasClayAt(x, y)

  private fun hasWaterAt(x: Int, y: Int) =
      water.contains(Coord17(x, y))

  private fun isWetAt(x: Int, y: Int) =
      wet.contains(Coord17(x, y))

  private fun layoutAsString(): String {
    val out = StringBuilder()
    (0..maxY).forEach { y ->
      (minX..maxX).forEach { x ->
        if (x == 500 && y == 0) {
          out.append('+')
        } else if (hasClayAt(x, y)) {
          out.append('#')
        } else if (hasWaterAt(x, y)) {
          out.append('~')
        } else if (isWetAt(x, y)) {
          out.append('|')
        } else {
          out.append('.')
        }
      }
      out.appendln()
    }
    return out.toString()
  }

  private fun hasClayAt(x: Int, y: Int) = clayCoords.contains(Coord17(x, y))

  private fun parseCoord(value: String): IntRange {
    return if (value.contains("..")) {
      val (start, end) = value.split("..").map(String::toInt)
      start..end
    } else {
      value.toInt()..value.toInt()
    }
  }
}

fun main(args: Array<String>) {
  val day = Day17()
  val input = day.javaClass
      .classLoader
      .getResourceAsStream("day17.txt")
      .bufferedReader()
      .readText()
  day.solve(input)
}