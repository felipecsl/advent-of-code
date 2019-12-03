package aoc2019

import java.lang.IllegalStateException

class Day2 {
  data class Instruction(
      val opcode: Int,
      val op1: Int,
      val op2: Int,
      val op3: Int
  )

  fun solve(noun: Int, verb: Int, input: MutableList<Int>): Int {
    input[1] = noun
    input[2] = verb
    var i = 0
    val instructions = mutableListOf<Instruction>()
    while (i < input.size && input[i] != 99) {
      instructions.add(Instruction(input[i++], input[i++], input[i++], input[i++]))
    }
    instructions.forEach {
      val result = when (it.opcode) {
        1 -> input[it.op1] + input[it.op2]
        2 -> input[it.op1] * input[it.op2]
        else -> throw IllegalStateException("Invalid opcode ${it.opcode}")
      }
      input[it.op3] = result
    }
    return input[0]
  }
}

fun main(@Suppress("UnusedMainParameter") args: Array<String>) {
  val day = Day2()
  val input = day.javaClass
      .classLoader
      .getResourceAsStream("2019/day2.txt")!!
      .bufferedReader()
      .readText()
  val formattedInput = input.split(",")
      .filter(String::isNotEmpty)
      .map(String::trim)
      .map(String::toInt)
  println("Part I: ${day.solve(12, 2, formattedInput.toMutableList())}")
  (0..99).forEach { noun ->
    (0..99).forEach { verb ->
      if (day.solve(noun, verb, formattedInput.toMutableList()) == 19690720) {
        println("Part II: ${100 * noun + verb}")
        return
      }
    }
  }
}