package aoc2018

enum class TrackDirection { HORIZ, VERT }

enum class CartDirection { LEFT, UP, RIGHT, DOWN }

data class Coords13(var x: Int, var y: Int) {
  override fun toString(): String {
    return "($x, $y)"
  }
}

class CrashException(val cartA: Cart, val cartB: Cart) : RuntimeException()

private val carts: MutableList<Cart> = mutableListOf()
private val allTracks: MutableList<Track> = mutableListOf()

data class Track(val direction: TrackDirection, val a: Coords13, val b: Coords13) {
  val start = if (a.y < b.y) a else b

  val end = if (a.y < b.y) b else a

  val isVertical = direction == TrackDirection.VERT

  val isHorizontal = direction == TrackDirection.HORIZ

  val length: Int
    get() {
      return when (direction) {
        TrackDirection.VERT -> b.y - a.y
        TrackDirection.HORIZ -> b.x - a.x
      }
    }
}

class Cart(
    var position: Coords13,
    var direction: CartDirection,
    val currentTracks: MutableSet<Track> = mutableSetOf()
) {
  private val turnOrders: List<(CartDirection) -> CartDirection> = listOf(
      { cd: CartDirection -> CartDirection.values()[Math.floorMod(cd.ordinal - 1, 4)] },
      { cd: CartDirection -> cd },
      { cd: CartDirection -> CartDirection.values()[Math.floorMod(cd.ordinal + 1, 4)] }
  )
  private var nextTurnIndex = 0

  override fun toString(): String {
    return "Cart(position=$position, direction=$direction, currentTracks=$currentTracks)"
  }

  private fun isGoingUp() = direction == CartDirection.UP

  private fun isGoingDown() = direction == CartDirection.DOWN

  fun move() {
    updateTracks()
    if (currentTracks.size == 1) {
      // happy path - just keep moving in the current direction
      doMove()
    } else {
      if (isAtCurve()) {
        // handle curve turn
        println("Cart $position at turn")
        makeTurn()
      } else {
        println("Cart $position at intersection")
        // handle intersection by determining the next turn direction
        direction = turnOrders[nextTurnIndex++ % turnOrders.size].invoke(direction)
        doMove()
      }
    }
  }

  private fun makeTurn() {
    when (direction) {
      CartDirection.LEFT -> moveUpOrDown()
      CartDirection.RIGHT -> moveUpOrDown()
      CartDirection.UP -> moveLeftOrRight()
      CartDirection.DOWN -> moveLeftOrRight()
    }
  }

  private fun doMove() {
    when (direction) {
      CartDirection.LEFT -> moveLeft()
      CartDirection.RIGHT -> moveRight()
      CartDirection.UP -> moveUp()
      CartDirection.DOWN -> moveDown()
    }
  }

  private fun moveLeftOrRight() {
    val nextTrack = currentTracks.first { it.isHorizontal }
    if (nextTrack.a == position) {
      if (nextTrack.b.x > nextTrack.a.x) moveRight() else moveLeft()
    } else {
      if (nextTrack.a.x > nextTrack.b.x) moveRight() else moveLeft()
    }
  }

  private fun moveUpOrDown() {
    val nextTrack = currentTracks.first { it.isVertical }
    if (nextTrack.a == position) {
      if (nextTrack.b.y > nextTrack.a.y) moveDown() else moveUp()
    } else {
      if (nextTrack.a.y > nextTrack.b.y) moveDown() else moveUp()
    }
  }

  private fun isAtEdgeOfTrack(track: Track): Boolean {
    return track.a == position || track.b == position
  }

  private fun isAtCurve(): Boolean {
    return currentTracks.size == 2
        && isAtEdgeOfTrack(currentTracks.first())
        && isAtEdgeOfTrack(currentTracks.last())
  }

  private fun updateTracks() {
    currentTracks.clear()
    for (t in allTracks) {
      when (t.direction) {
        TrackDirection.HORIZ -> {
          val y = t.a.y
          (t.a.x..t.b.x)
              .filter { position.x == it && position.y == y }
              .forEach { currentTracks.add(t) }
        }
        TrackDirection.VERT -> {
          val x = t.a.x
          (t.start.y..t.end.y)
              .filter { position.y == it && position.x == x }
              .forEach { currentTracks.add(t) }
        }
      }
    }
    if (currentTracks.isEmpty()) {
      throw IllegalStateException("Could not find any Tracks for cart $this")
    }
  }

  private fun moveDown() {
    position = position.copy(y = position.y + 1)
    direction = CartDirection.DOWN
  }

  private fun moveUp() {
    position = position.copy(y = position.y - 1)
    direction = CartDirection.UP
  }

  private fun moveRight() {
    position = position.copy(x = position.x + 1)
    direction = CartDirection.RIGHT
  }

  private fun moveLeft() {
    position = position.copy(x = position.x - 1)
    direction = CartDirection.LEFT
  }
}

class Day13 {
  fun solve(input: String): String {
    parseCartsAndTracks(input)
//    allTracks.forEach(::println)
    carts.forEach(::println)
    println()
    while (true) {
      try {
        moveCarts()
      } catch (e: CrashException) {
        println("Crash between ${e.cartA} and ${e.cartB}")
        return e.cartA.position.toString()
      }
      carts.forEach(::println)
      println()
    }
  }

  private fun moveCarts() {
    carts.forEach { c ->
      c.move()
      checkForCrash()
    }
  }

  private fun checkForCrash() {
    val cartsInTheSamePosition = carts.groupBy { it.position }.values.firstOrNull { it.size > 1 }
    if (cartsInTheSamePosition != null) {
      var (a, b) = cartsInTheSamePosition
      throw CrashException(a, b)
    }
  }

  private fun parseCartsAndTracks(input: String) {
    val lines = input.lines()
    var horizTrackStart: Int
    val startedVertTracks = mutableListOf<Coords13>()
    lines.forEachIndexed { i, l ->
      horizTrackStart = -1
      l.forEachIndexed { j, c ->
        when (c) {
          '/' -> {
            if (j < l.length - 1 &&
                (l[j + 1] == '-' || l[j + 1] == '+' || l[j + 1] == '>')) {
              horizTrackStart = j
            } else if (horizTrackStart != -1) {
              allTracks.add(Track(TrackDirection.HORIZ,
                  Coords13(horizTrackStart, i),
                  Coords13(j, i)))
              horizTrackStart = -1
            }
            if (i < lines.size - 1 && j < lines[i + 1].length &&
                (lines[i + 1][j] == '|' || lines[i + 1][j] == '+' || lines[i + 1][j] == 'v')) {
              startedVertTracks.add(Coords13(j, i))
            } else if (startedVertTracks.isNotEmpty()) {
              val vertTrackStart = startedVertTracks.first { it.x == j }
              allTracks.add(Track(TrackDirection.VERT,
                  Coords13(j, vertTrackStart.y),
                  Coords13(j, i)))
              startedVertTracks.remove(vertTrackStart)
            }
          }
          '\\' -> {
            if (j < l.length - 1 && (l[j + 1] == '-' || l[j + 1] == '+')) {
              horizTrackStart = j
            } else if (horizTrackStart != -1) {
              allTracks.add(Track(TrackDirection.HORIZ,
                  Coords13(horizTrackStart, i),
                  Coords13(j, i)))
              horizTrackStart = -1
            }
            if (i < lines.size - 1 && j < lines[i + 1].length &&
                (lines[i + 1][j] == '|' || lines[i + 1][j] == '+' || lines[i + 1][j] == 'v')) {
              startedVertTracks.add(Coords13(j, i))
            } else if (startedVertTracks.isNotEmpty()) {
              val vertTrackStart = startedVertTracks.first { it.x == j }
              allTracks.add(Track(TrackDirection.VERT,
                  Coords13(j, vertTrackStart.y),
                  Coords13(j, i)))
              startedVertTracks.remove(vertTrackStart)
            }
          }
          '>' -> carts.add(Cart(Coords13(j, i), CartDirection.RIGHT))
          '<' -> carts.add(Cart(Coords13(j, i), CartDirection.LEFT))
          '^' -> carts.add(Cart(Coords13(j, i), CartDirection.UP))
          'v' -> carts.add(Cart(Coords13(j, i), CartDirection.DOWN))
        }
      }
    }
  }
}

fun main(args: Array<String>) {
  val day13 = Day13()
  val input = day13.javaClass
      .classLoader
      .getResourceAsStream("day13.txt")
      .bufferedReader()
      .readText()
  println(day13.solve(input))
}