package aoc2018

class Day18 {
  fun solve(input: String): Int {
    val totalLines = input.lines().size
    val area = Array(totalLines) { CharArray(input.lines()[0].length) }
    input.lines().forEachIndexed { y, l ->
      l.forEachIndexed { x, c ->
        area[y][x] = c
      }
    }
    (1..10).forEach { ct ->
      val original = Array(totalLines) { area[it].copyOf() }
      original.forEachIndexed { y, row ->
        row.forEachIndexed { x, c ->
          val adjacent = adjacentAcresTo(original, x, y)
          val new = when (c) {
            '.' -> if (adjacent.count { it == '|' } >= 3) '|' else '.'
            '#' -> if (adjacent.count { it == '#' } > 0 && adjacent.count { it == '|' } > 0) '#' else '.'
            '|' -> if (adjacent.count { it == '#' } >= 3) '#' else '|'
            else -> throw RuntimeException()
          }
          area[y][x] = new
        }
      }
    }
    return countTotal(area)
  }

  private fun countTotal(area: Array<CharArray>): Int {
    val totalWood = area.sumBy { it.count { it == '|' } }
    val totalLumber = area.sumBy { it.count { it == '#' } }
    return totalWood * totalLumber
  }

  private fun adjacentAcresTo(area: Array<CharArray>, x: Int, y: Int): List<Char> {
    val y0 = maxOf(0, y - 1)
    val y1 = minOf(area.size - 1, y + 1)
    val x0 = maxOf(0, x - 1)
    val x1 = minOf(area[0].size - 1, x + 1)
    val adjacentAcres: MutableList<Char> = mutableListOf()
    for (_y in y0..y1) {
      for (_x in x0..x1) {
        if (_x == x && _y == y) {
          continue
        }
        adjacentAcres.add(area[_y][_x])
      }
    }
    return adjacentAcres
  }
}

fun main(args: Array<String>) {
  val day = Day18()
  val input = day.javaClass
      .classLoader
      .getResourceAsStream("day18.txt")
      .bufferedReader()
      .readText()
  println("Part I: ${day.solve(input)}")
}