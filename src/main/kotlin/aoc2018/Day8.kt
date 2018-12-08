package aoc2018

data class Day8Node(
    val name: Int,
    val childCount: Int,
    val metadataCount: Int,
    val metadata: MutableList<Int> = mutableListOf(),
    val childNodes: MutableList<Day8Node> = mutableListOf()
) {
  fun sumOfMetadataEntries(): Int =
      metadata.sum() + childNodes.sumBy(Day8Node::sumOfMetadataEntries)

  fun value(): Int {
    return if (childNodes.isEmpty()) {
      metadata.sum()
    } else {
      metadata.map { i ->
        if (i == 0 || i - 1 >= childNodes.size) 0 else childNodes[i - 1].value()
      }.sum()
    }
  }

  override fun toString(): String {
    return "Day8Node(name=$name, childCount=$childCount, metadataCount=$metadataCount, metadata=$metadata, childNodes=$childNodes)"
  }
}

class Day8 {
  private var nodeCounter = 0
  private lateinit var root: Day8Node

  fun solve() {
    val input = javaClass.classLoader.getResourceAsStream("day8.txt")
        .bufferedReader()
        .readText()
        .trim()
    println("Part I: ${partI(input)}")
    println("Part II: ${partII()}")
  }

  fun partI(input: String): Int {
    val nums = input.split(" ").map(String::toInt).toMutableList()
    return if (nums.size >= 2) {
      parseNodes(nums, null)
      root.sumOfMetadataEntries()
    } else {
      0
    }
  }

  fun partII(): Int {
    return root.value()
  }

  private fun parseNodes(nums: MutableList<Int>, currentNode: Day8Node?) {
    if (nums.size < 2) {
      return
    } else {
      val node = parseNode(nums)
      if (currentNode == null) root = node else currentNode.childNodes.add(node)
      for (it in 0 until node.childCount) parseNodes(nums, node)
      (0 until node.metadataCount).forEach { node.metadata.add(nums.removeAt(0)) }
    }
  }

  private fun parseNode(nums: MutableList<Int>): Day8Node {
    return Day8Node(nodeCounter++, nums.removeAt(0), nums.removeAt(0))
  }
}

fun main(args: Array<String>) {
  Day8().solve()
}