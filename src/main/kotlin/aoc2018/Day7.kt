package aoc2018

import java.util.*

class Node(
    val name: Char,
    val nextNodes: SortedSet<Node> = TreeSet<Node> { a, b -> a.name.compareTo(b.name) },
    var startTime: Int = 0
) {
  val duration = name.toInt() - 4

  override fun toString(): String {
    return "Node(name='$name', startTime=$startTime, nextNodes=${nextNodes.map { it.name }})"
  }

  fun isLeaf() = nextNodes.isEmpty()

  fun hasPathTo(other: Node): Boolean {
    if (this == other) {
      return true
    } else {
      for (node in nextNodes) {
        if (node.hasPathTo(other)) {
          return true
        }
      }
      return false
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Node

    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }
}

class Day7 {
  private val comparator = Comparator.comparing(Node::name)
  private var roots: SortedSet<Node> = TreeSet(comparator)
  private val namesToNodes: MutableMap<Char, Node> = mutableMapOf()

  private fun allNodes() = namesToNodes.values

  fun solve() {
    val input = javaClass.classLoader.getResourceAsStream("day7.txt")
        .bufferedReader()
        .readLines()
    solveForInput(input)
  }

  fun solveForInput(input: List<String>): String {
    input.forEach {
      val tokens = it.split(" ")
      val nextNodeName = tokens[7][0]
      val thisNodeName = tokens[1][0]
      val nextNode = namesToNodes[nextNodeName] ?: initNode(nextNodeName)
      val thisNode = namesToNodes[thisNodeName] ?: initNode(thisNodeName)
      thisNode.nextNodes.add(nextNode)
    }
    findRoots()
    val path = determineOrder()
    printNodes()
    println("Part I: $path")
    val timeToComplete = determineTimeToComplete()
    return path
  }

  private fun determineTimeToComplete(totalWorkers: Int = 5): Int {
//    val currTime = 0
//    val workers = IntArray(totalWorkers)
//    val queue: SortedSet<Node> = TreeSet(comparator)
//    queue.addAll(roots)
//    while ()
    return 0
  }

  private fun determineOrder(): String {
    val queue: SortedSet<Node> = TreeSet(comparator)
    val visitedPath: MutableSet<Node> = mutableSetOf()
    queue.addAll(roots)
    while (!queue.isEmpty()) {
      val node = queue.firstOrNull { arePrerequisitesMetFor(it, visitedPath) }
          ?: throw IllegalStateException("Cant find suitable node to visit. Current path $visitedPath")
      queue.remove(node)
      visitedPath.add(node)
      node.nextNodes.forEach {
        it.startTime = node.startTime + node.duration
      }
      queue.addAll(node.nextNodes)
    }
    return visitedPath.map(Node::name).joinToString("")
  }

  private fun arePrerequisitesMetFor(destNode: Node, visitedNodes: Set<Node>): Boolean {
    return allNodes()
        .filter { it != destNode && it.hasPathTo(destNode) }
        .all { visitedNodes.contains(it) }
  }

  private fun findRoots() {
    val nonRootNodes = namesToNodes.values.flatMap(Node::nextNodes).toSet()
    // resulting collection should have a single item only
    roots = (allNodes() - nonRootNodes).toSortedSet(comparator)
    println("Root nodes are $roots")
  }

  private fun printNodes() {
    namesToNodes.values.toSet().forEach {
      println(it)
    }
  }

  private fun initNode(nodeName: Char): Node {
    val node = Node(nodeName)
    namesToNodes[nodeName] = node
    return node
  }
}

fun main(args: Array<String>) {
  Day7().solve()
}

