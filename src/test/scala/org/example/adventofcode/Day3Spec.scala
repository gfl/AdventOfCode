package org.example.adventofcode

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day3Spec extends AnyFlatSpec with Matchers {

  import Day3._

  behavior of "calculatePoints"

  it should "calculate points for right direction" in {
    calculatePoints((0, 0), Movement(Right, 1)) should be(List((1, 0)))
  }

  it should "calculate points for left direction" in {
    calculatePoints((1, 0), Movement(Left, 2)) should be(List((0, 0), (-1, 0)))
  }

  it should "calculate points for up direction" in {
    calculatePoints((1, 0), Movement(Up, 3)) should be(List((1, 1), (1, 2), (1, 3)))
  }

  it should "calculate points for down direction" in {
    calculatePoints((1, 1), Movement(Down, 4)) should be(List((1, 0), (1, -1), (1, -2), (1, -3)))
  }

  behavior of "representPoints"

  it should "represent the positions of a wire relative to the center" in {
    representPoints(Array("R1", "U1", "L1", "D1")) should be(List((0, 0), (1, 0), (1, 1), (0, 1), (0, 0)))
  }

  behavior of "calculateIntersections"

  it should "calculate the interecting points ignoring the origin" in {
    calculateIntersections(List((0, 0), (1, 0), (2, 0), (2, 1), (2, 2)),
      List((0, 0), (0, 1), (0, 2), (1, 2), (2, 2))) should be(List((2, 2)))
  }

  behavior of "getManhattanDistanceToClosestIntersection"

  it should "get the Manhattan distance to the closest intersection for the first example" in {
    val wire1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72".split(",")
    val wire2 = "U62,R66,U55,R34,D71,R55,D58,R8".split(",")
    getManhattanDistanceToClosestIntersection(wire1, wire2) should be(159)
  }

  it should "get the Manhattan distance to the closest intersection for the second example" in {
    val wire1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51".split(",")
    val wire2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7".split(",")
    getManhattanDistanceToClosestIntersection(wire1, wire2) should be(135)
  }

  behavior of "getMinimumStepsToIntersection"

  it should "get the minimum steps to one of the intersections for the first example" in {
    val wire1 = "R75,D30,R83,U83,L12,D49,R71,U7,L72".split(",")
    val wire2 = "U62,R66,U55,R34,D71,R55,D58,R8".split(",")
    getMinStepsToIntersection(wire1, wire2) should be(610)
  }

  it should "get the minimum steps to one of the intersections for the second example" in {
    val wire1 = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51".split(",")
    val wire2 = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7".split(",")
    getMinStepsToIntersection(wire1, wire2) should be(410)
  }


}
