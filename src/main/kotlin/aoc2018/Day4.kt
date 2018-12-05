package aoc2018

import java.lang.IllegalStateException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale


enum class EventType {
  WAKE_UP, FALL_ASLEEP, BEGIN_SHIFT
}

fun parseEvent(input: String): EventType {
  return when (input) {
    "falls asleep" -> EventType.FALL_ASLEEP
    "wakes up" -> EventType.WAKE_UP
    "begins shift" -> EventType.BEGIN_SHIFT
    else -> throw IllegalArgumentException(input)
  }
}

class Guard(
    var id: Int? = null,
    var asleepMins: Int = 0,
    val allAsleepMins: MutableList<Int> = mutableListOf()
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Guard

    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    return id ?: 0
  }

  override fun toString(): String {
    return "Guard(id=$id, asleepMins=$asleepMins, allAsleepMins=$allAsleepMins)"
  }
}

data class Event(
    val timestamp: Instant,
    val type: EventType,
    var guard: Guard
)

class Day4 {
  fun solve() {
    val events = parseEvents()
    val eventsByGuard = events.groupBy(Event::guard)
    eventsByGuard.forEach { (guard, events) ->
      events.forEachIndexed { i, e ->
        if (e.type == EventType.FALL_ASLEEP) {
          val fallAsleepEvent = e
          val wakeUpEvent = events[i + 1]
          if (wakeUpEvent.type != EventType.WAKE_UP) {
            throw IllegalStateException()
          }
          val asleepMins = fallAsleepEvent.timestamp.until(wakeUpEvent.timestamp, ChronoUnit.MINUTES)
          val startMinute = minuteFormatter.format(fallAsleepEvent.timestamp).toInt()
          val endMinute = minuteFormatter.format(wakeUpEvent.timestamp).toInt()
          guard.asleepMins += asleepMins.toInt()
          guard.allAsleepMins.addAll((startMinute until endMinute).toList())
        }
      }
    }
    val allGuards = eventsByGuard.keys
    val mostAsleepGuard = allGuards.maxBy(Guard::asleepMins)!!
    val mostAsleepMinute = mostAsleepGuard.mostFrequentlySleptMinute()
    val partIGuardId = mostAsleepGuard.id
    println("Part I: ${partIGuardId!! * mostAsleepMinute}")
    val guardMostFrequentlyAsleepOnTheSameMinute = allGuards.maxBy {
      it.mostTimesAsleepOnTheSameMinute()
    }!!
    val partIIGuardId = guardMostFrequentlyAsleepOnTheSameMinute.id!!
    val mostFrequentlySleptMinute = guardMostFrequentlyAsleepOnTheSameMinute.mostFrequentlySleptMinute()
    println("Part II: ${partIIGuardId * mostFrequentlySleptMinute}")
  }

  private fun Guard.mostFrequentlySleptMinute() =
      allAsleepMins
          .groupBy { it }
          .values
          .maxBy { it.size }!!
          .first()

  private fun Guard.mostTimesAsleepOnTheSameMinute() =
      allAsleepMins
          .groupBy { it }
          .values
          .maxBy { it.size }
          ?.size ?: 0

  private fun parseEvents(): List<Event> {
    val events = javaClass.classLoader.getResourceAsStream("day4.txt")
        .bufferedReader()
        .readLines()
        .map { parseEventLine(it) }
        .sortedBy { it.timestamp.epochSecond }
    events.forEachIndexed { i, event ->
      if (event.guard.id == null) {
        event.guard.id = events[i - 1].guard.id
      }
    }
    return events
  }

  private fun parseEventLine(it: String): Event {
    val matcher = PATTERN.matcher(it)
    matcher.matches()
    val temporalAccessor = dateFormat.parse(
        "${matcher.group(1)}-${matcher.group(2)}-${matcher.group(3)} ${matcher.group(4)}:${matcher.group(5)}")
    val localDateTime = LocalDateTime.from(temporalAccessor)
    val zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of("Z"))
    val timestamp = Instant.from(zonedDateTime)
    val event = matcher.group(6)
    val eventType = if (event.endsWith("begins shift")) "begins shift" else event
    val guardIdMatcher = "Guard #(\\d+) (.+)".toPattern().matcher(event)
    val guardId = if (guardIdMatcher.matches()) guardIdMatcher.group(1).toInt() else null
    return Event(timestamp, parseEvent(eventType), Guard(guardId))
  }

  companion object {
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    private val minuteFormatter = DateTimeFormatter.ofPattern("mm")
        .withLocale(Locale.US)
        .withZone(ZoneId.of("Z"))
    private val PATTERN = "\\[(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d) (\\d\\d):(\\d\\d)] (.+)".toPattern()
  }
}

fun main(args: Array<String>) {
  Day4().solve()
}
