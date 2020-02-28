package org.example.adventofcode

import scala.io.Source


object Day3 extends App {

  sealed trait Direction

  case object Up extends Direction

  case object Down extends Direction

  case object Left extends Direction

  case object Right extends Direction

  case class Movement(direction: Direction, steps: Int)

  object Movement {
    def apply(movement: String): Movement = {
      val direction = movement(0) match {
        case 'U' => Up
        case 'D' => Down
        case 'L' => Left
        case 'R' => Right
        case _ =>
          throw new IllegalArgumentException(s"Direction '${movement(0)}' not recognised. Valid values are: U, D, L or R.")
      }
      val steps: Int = movement.tail.toInt
      assert(steps > 0)
      new Movement(direction, steps)
    }
  }

  def calculatePoints(currentPosition: (Int, Int), movement: Movement): List[(Int, Int)] = {
    val (x, y) = currentPosition
    val increments: List[Int] = (1 to movement.steps).toList
    movement.direction match {
      case Up => increments.map(inc => (x, y + inc))
      case Down => increments.map(inc => (x, y - inc))
      case Left => increments.map(inc => (x - inc, y))
      case Right => increments.map(inc => (x + inc, y))
    }
  }

  def representPoints(movements: Array[String]): List[(Int, Int)] = {
    movements.map(Movement(_)).foldLeft(List((0, 0)))((acc, movement) => {
      acc ++ calculatePoints(acc.last, movement)
    })
  }

  def calculateIntersections(positions1: List[(Int, Int)], positions2: List[(Int, Int)]): List[(Int, Int)] = {
    positions1.intersect(positions2).filterNot(_ == (0, 0))
  }

  /**
   * Manhattan distance.
   * Definition: The distance between two points measured along axes at right angles.
   * In a plane with p1 at (x1, y1) and p2 at (x2, y2), it is |x1 - x2| + |y1 - y2|.
   * Lm distance.
   *
   * @param point1 (x1, y1)
   * @param point2 (x2, y2)
   * @return distance
   */
  def calculateManhattanDistance(point1: (Int, Int), point2: (Int, Int)): Int = {
    val (x1, y1) = point1
    val (x2, y2) = point2
    math.abs(x1 - x2) + math.abs(y1 - y2)
  }

  def getManhattanDistanceToClosestIntersection(wire1: Array[String], wire2: Array[String]): Int = {
    val pointsWire1 = representPoints(wire1)
    val pointsWire2 = representPoints(wire2)
    val intersectingPoints = calculateIntersections(pointsWire1, pointsWire2)
    intersectingPoints.map(calculateManhattanDistance(_, (0, 0))).min
  }

  def calculateStepsToPoints(points: List[(Int,Int)]): Map[(Int, Int), Int] = {
    points.zipWithIndex.groupBy(_._1).map { case (point, list) => (point, list.map(_._2).min) }
  }

  def getMinStepsToIntersection(wire1: Array[String], wire2: Array[String]): Int = {
    val pointsWire1 = representPoints(wire1)
    val pointsWire2 = representPoints(wire2)
    val intersectingPoints = calculateIntersections(pointsWire1, pointsWire2)
    val stepsToPointsWire1 = calculateStepsToPoints(pointsWire1)
    val stepsToPointsWire2 = calculateStepsToPoints(pointsWire2)
    intersectingPoints.map(point => stepsToPointsWire1(point) + stepsToPointsWire2(point)).min
  }

  val inputData = Source.fromResource("day3/input.txt").getLines()
  val wire1 = inputData.next().split(",")
  val wire2 = inputData.next().split(",")
  val result = getManhattanDistanceToClosestIntersection(wire1, wire2)
  println(s"Manhattan distance from the central port to the closest intersection is ${result}")

  val result2 = getMinStepsToIntersection(wire1, wire2)
  println(s"The minimum steps to intersection are $result2")

}
