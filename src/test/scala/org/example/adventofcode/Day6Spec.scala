package org.example.adventofcode

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day6Spec extends AnyFlatSpec with Matchers {

  import Day6._

  behavior of "countOrbits"

  it should "return 0 for a map without orbits" in {
    countOrbits(Map()) should be(0)
    countOrbits(Map("COM" -> List())) should be(0)
  }

  it should "return 1 for a map with one orbit" in {
    countOrbits(Map("COM" -> List("A"))) should be(1)
  }

  it should "return 2 for a map with 2 independent orbits" in {
    countOrbits(Map("COM" -> List("A", "B"))) should be(2)
  }

  it should "return 3 for a map with a transitive orbit" in {
    countOrbits(Map("COM" -> List("A"), "A" -> List("B"))) should be(3)
  }

  it should "return 6 for a map with a twice transitive orbit" in {
    countOrbits(Map("COM" -> List("A"), "A" -> List("B"), "B" -> List("C"))) should be(6)
  }

  it should "calculate correctly the orbit count for the given example" in {
    val map = Map(
      "COM" -> List("B"),
      "B" -> List("G", "C"),
      "C" -> List("D"),
      "D" -> List("E", "I"),
      "E" -> List("F", "J"),
      "G" -> List("H"),
      "J" -> List("K"),
      "K" -> List("L")
    )
    countOrbits(map) should be(42)
  }

  behavior of "findShortestPathBetween2Planets"

  it should "find the path between 2 planets in the same orbit" in {
    findShortestPathBetween2Planets(List("COM", "A"), List("COM", "B")) should be(List("COM"))
  }

  it should "find the path between 2 planets that are not in the same orbit" in {
    findShortestPathBetween2Planets(List("COM", "A", "B", "C"), List("COM", "B", "D", "E")) should be(List("B", "D"))
  }

  it should "find the path between YOU and SANTA in the provided example" in {
    val pathToYOU = List("COM", "B", "C", "D", "E", "J", "K", "YOU")
    val pathToSAN = List("COM", "B", "C", "D", "I", "SAN")

    findShortestPathBetween2Planets(pathToYOU, pathToSAN) should be(List("K", "J", "E", "D", "I"))
  }

}
