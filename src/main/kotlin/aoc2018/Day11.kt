package aoc2018

data class Square(val x: Int, val y: Int, val size: Int, val totalPower: Int)

class Day11(private val serialNumber: Int) {
  private val powerLevels: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
  private val totalPowers: MutableMap<Triple<Int, Int, Int>, Int> = mutableMapOf()

  fun solve() {
    solvePartI()
    solvePartII()
  }

  private fun solvePartI() {
    val size = 3
    val max = (0 until 300 - size).flatMap { y ->
      (0 until 300 - size).map { x ->
        Square(x, y, size, totalPower(x, y, size))
      }
    }.maxBy { it.totalPower }!!
    println("Part I: ${max.x},${max.y}")
  }

  private fun solvePartII() {
    println("Part II: ")
    val max = (1 until 300).flatMap { size ->
      print(".")
      (0 until 300 - size).flatMap { y ->
        (0 until 300 - size).map { x ->
          Square(x, y, size, totalPower(x, y, size))
        }
      }
    }.maxBy { it.totalPower }!!
    println("\n${max.x},${max.y},${max.size}")
  }

  private fun powerLevel(x: Int, y: Int): Int {
    val rackId = x + 10
    val powerLevel = ((rackId * y + serialNumber) * rackId).toString()
    return if (powerLevel.length >= 3) {
      powerLevel[powerLevel.length - 3].toString().toInt()
    } else {
      0
    } - 5
  }

  private fun totalPower(x: Int, y: Int, size: Int): Int {
    return if (size == 1) {
      calcTotalPower(x, y, size).let {
        totalPowers[Triple(x, y, size)] = it
        it
      }
    } else {
      val prevSize = totalPowers[Triple(x, y, size - 1)] ?: calcTotalPower(x, y, size).let {
        totalPowers[Triple(x, y, size)] = it
        it
      }
      val lastRow = (x until x + size).map { xa ->
        calcPowerLevel(xa, y + size - 1)
      }.sum()
      val lastCol = (y until y + size - 1).map { ya ->
        calcPowerLevel(x + size - 1, ya)
      }.sum()
      (prevSize + lastRow + lastCol).let {
        totalPowers[Triple(x, y, size)] = it
        it
      }
    }
  }

  private fun calcTotalPower(x: Int, y: Int, size: Int): Int {
    return (y until y + size).map { ya ->
      (x until x + size).map { xa ->
        calcPowerLevel(xa, ya)
      }.sum()
    }.sum()
  }

  private fun calcPowerLevel(xa: Int, ya: Int): Int {
    return powerLevels[xa to ya] ?: powerLevel(xa, ya).let {
      powerLevels[xa to ya] = it
      it
    }
  }
}

fun main(args: Array<String>) {
  println(Day11(9306).solve())
}