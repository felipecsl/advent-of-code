package aoc2018

/**
--- Day 6: Chronal Coordinates ---
The device on your wrist beeps several times, and once again you feel like you're falling.

"Situation critical," the device announces. "Destination indeterminate. Chronal interference detected. Please specify new target coordinates."

The device then produces a list of coordinates (your puzzle input). Are they places it thinks are safe or dangerous? It recommends you check manual page 729. The Elves did not give you a manual.

If they're dangerous, maybe you can minimize the danger by finding the coordinate that gives the largest distance from the other points.

Using only the Manhattan distance, determine the area around each coordinate by counting the number of integer X,Y locations that are closest to that coordinate (and aren't tied in distance to any other coordinate).

Your goal is to find the size of the largest area that isn't infinite. For example, consider the following list of coordinates:

1, 1
1, 6
8, 3
3, 4
5, 5
8, 9
If we id these coordinates A through F, we can draw them on a grid, putting 0,0 at the top left:

..........
.A........
..........
........C.
...D......
.....E....
.B........
..........
..........
........F.
This view is partial - the actual grid extends infinitely in all directions. Using the Manhattan distance, each location's closest coordinate can be determined, shown here in lowercase:

aaaaa.cccc
aAaaa.cccc
aaaddecccc
aadddeccCc
..dDdeeccc
bb.deEeecc
bBb.eeee..
bbb.eeefff
bbb.eeffff
bbb.ffffFf
Locations shown as . are equally far from two or more coordinates, and so they don't count as being closest to any.

In this example, the areas of coordinates A, B, C, and F are infinite - while not shown here, their areas extend forever outside the visible grid. However, the areas of coordinates D and E are finite: D is closest to 9 locations, and E is closest to 17 (both including the coordinate's location itself). Therefore, in this example, the size of the largest area is 17.


 */

var COORD_COUNTER = 0

data class Coord(
    val x: Int,
    val y: Int,
    val id: Int,
    var isInfinite: Boolean = false
)

data class PointWithTotalDistance(
    val x: Int,
    val y: Int,
    val totalDistance: Int
)

class Day6 {
  fun solve() {
    val input = javaClass.classLoader.getResourceAsStream("day6.txt")
        .bufferedReader()
    val (part1, part2) = solveForInput(input.readLines())
    println("Part I: $part1")
    println("Part II: $part2")
  }

  fun findClosestCoordTo(x: Int, y: Int, coordinates: List<Coord>): Coord? {
    // handle case when multiple coords have the same distance to the point
    val sortedDistances = coordinates.map { it.distanceTo(x, y) to it }.sortedBy { it.first }
    return if (sortedDistances.isEmpty()) {
      null
    } else if (sortedDistances.size == 1) {
      sortedDistances[0].second
    } else {
      if (sortedDistances[0].first == sortedDistances[1].first) null else sortedDistances[0].second
    }
  }

  fun solveForInput(input: List<String>, regionMaxTotalDistance: Int = 10_000): Pair<Int, Int> {
    val coordinates = input.map {
      val (x, y) = it.split(", ").map(String::toInt)
      Coord(x, y, COORD_COUNTER++)
    }
    val topMostCoord = coordinates.minBy { it.y }!!
    val bottomMostCoord = coordinates.maxBy { it.y }!!
    val leftMostCoord = coordinates.minBy { it.x }!!
    val rightMostCoord = coordinates.maxBy { it.x }!!
    val outerCoordIds = listOf(
        topMostCoord.id,
        bottomMostCoord.id,
        leftMostCoord.id,
        rightMostCoord.id
    )
    val points = mutableListOf<Coord>()
    val distances = mutableListOf<PointWithTotalDistance>()
    (topMostCoord.y - 1 .. bottomMostCoord.y).forEach { y ->
      (leftMostCoord.x - 1 .. rightMostCoord.x + 1).forEach { x ->
        val matchingCoord = coordinates.firstOrNull { it.x == x && it.y == y }
        val totalDistances = coordinates.sumBy { it.distanceTo(x, y) }
        if (totalDistances < regionMaxTotalDistance) {
          distances.add(PointWithTotalDistance(x, y, totalDistances))
        }
        if (matchingCoord != null) {
          // found a coord at this point
          val formattedId = String.format("%02d", matchingCoord.id)
          if (!outerCoordIds.contains(matchingCoord.id)) {
            points.add(matchingCoord)
          }
//          print("[$formattedId] ")
        } else {
          val closestCoord = findClosestCoordTo(x, y, coordinates)
          if (closestCoord != null) {
            // there's a single coord that's the closest one to this point
            val formattedId = String.format("%02d", closestCoord.id)
            if (!outerCoordIds.contains(closestCoord.id)) {
              points.add(closestCoord)
//              if (totalDistances < regionMaxTotalDistance) {
//                print("|##| ")
//              } else {
//                print("|$formattedId| ")
//              }
              // check for edges
              if (x == leftMostCoord.x - 1
                  || x == rightMostCoord.x + 1
                  || y == topMostCoord.y - 1
                  || y == bottomMostCoord.y - 1) {
                closestCoord.isInfinite = true
              }
            } else {
//              print("|$formattedId| ")
            }
          } else {
            // multiple coords match with same distance
            if (totalDistances < regionMaxTotalDistance) {
//              print("|##| ")
            } else {
//              print("|..| ")
            }
          }
        }
      }
//      println()
    }

    val largestAreasFirst = points
        .filter { !it.isInfinite }
        .groupBy { it }
        .values
        .sortedByDescending { it.size }
    val sizeOfRegion = distances.size
    return largestAreasFirst[0].size to sizeOfRegion
  }
}

fun Coord.distanceTo(x: Int, y: Int): Int {
  val xDist = Math.abs(this.x - x)
  val yDist = Math.abs(this.y - y)
  return xDist + yDist
}

fun main(args: Array<String>) {
  Day6().solve()
}