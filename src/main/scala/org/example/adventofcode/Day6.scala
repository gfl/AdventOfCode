package org.example.adventofcode

import scala.collection.mutable
import scala.io.Source

/**
 * --- Day 6: Universal Orbit Map ---
 * You've landed at the Universal Orbit Map facility on Mercury. Because navigation in space often involves transferring between orbits, the orbit maps here are useful for finding efficient routes between, for example, you and Santa. You download a map of the local orbits (your puzzle input).
 *
 * Except for the universal Center of Mass (COM), every object in space is in orbit around exactly one other object. An orbit looks roughly like this:
 *
 * \
 * \
 * |
 * |
 * AAA--> o            o <--BBB
 * |
 * |
 * /
 * /
 * In this diagram, the object BBB is in orbit around AAA. The path that BBB takes around AAA (drawn with lines) is only partly shown. In the map data, this orbital relationship is written AAA)BBB, which means "BBB is in orbit around AAA".
 *
 * Before you use your map data to plot a course, you need to make sure it wasn't corrupted during the download. To verify maps, the Universal Orbit Map facility uses orbit count checksums - the total number of direct orbits (like the one shown above) and indirect orbits.
 *
 * Whenever A orbits B and B orbits C, then A indirectly orbits C. This chain can be any number of objects long: if A orbits B, B orbits C, and C orbits D, then A indirectly orbits D.
 *
 * For example, suppose you have the following map:
 *
 * COM)B
 * B)C
 * C)D
 * D)E
 * E)F
 * B)G
 * G)H
 * D)I
 * E)J
 * J)K
 * K)L
 * Visually, the above map of orbits looks like this:
 *
 * G - H       J - K - L
 * /           /
 * COM - B - C - D - E - F
 * \
 * I
 * In this visual representation, when two objects are connected by a line, the one on the right directly orbits the one on the left.
 *
 * Here, we can count the total number of orbits as follows:
 *
 * D directly orbits C and indirectly orbits B and COM, a total of 3 orbits.
 * L directly orbits K and indirectly orbits J, E, D, C, B, and COM, a total of 7 orbits.
 * COM orbits nothing.
 * The total number of direct and indirect orbits in this example is 42.
 *
 * What is the total number of direct and indirect orbits in your map data?
 */
object Day6 extends App {

  def countOrbits(links: Map[String, List[String]]): Int = {

    def countByLevel(currentLevel: String, currentCount: Int): Int = {
      val mayNextLevel = links.get(currentLevel)
      if (mayNextLevel.isDefined) {
        mayNextLevel.get.map { elem => countByLevel(elem, currentCount + 1)
        }.sum + currentCount
      } else {
        currentCount
      }
    }

    countByLevel("COM", 0)

  }

  def calculatePathToPlanet(planet: String, parentMap: Map[String, String]): List[String] = {
    @scala.annotation.tailrec
    def getPath(currentPlanet: String, pathUntilCurrentPlanet: List[String]): List[String] = {
      val mayParent = parentMap.get(currentPlanet)
      if (mayParent.isDefined) {
        getPath(mayParent.get, currentPlanet :: pathUntilCurrentPlanet)
      } else {
        currentPlanet :: pathUntilCurrentPlanet
      }
    }

    getPath(planet, List())
  }

  def findShortestPathBetween2Planets(planet1Path: List[String], planet2Path: List[String]): List[String] = {
    val reversedPath = planet1Path.reverse.tail.iterator
    val pathFromOriginToClosestIntersection = mutable.ArrayBuffer[String]()
    do {
      pathFromOriginToClosestIntersection.append(reversedPath.next())
    } while (reversedPath.hasNext && !planet2Path.contains(pathFromOriginToClosestIntersection.last))
    val planet2PathIntersectionIndex = planet2Path.indexOf(pathFromOriginToClosestIntersection.last)
    val pathFromClosestIntersectionToDestination = planet2Path.slice(planet2PathIntersectionIndex + 1,
      planet2Path.length - 1)
    pathFromOriginToClosestIntersection.toList ++ pathFromClosestIntersectionToDestination
  }

  def countStepsToShortestPath(planet1Path: List[String], planet2Path: List[String]): Int = {
    findShortestPathBetween2Planets(planet1Path, planet2Path).length - 1
  }

  val inputMap = mutable.Map[String, List[String]]()
  val parentMap = mutable.Map[String, String]()
  for (input: Array[String] <- Source.fromResource("day6/input.txt").getLines.map(_.split("\\)"))) {
    val origin = input(0)
    val destination = input(1)
    val existingDestinations = inputMap.getOrElse(origin, List())
    inputMap(origin) = destination :: existingDestinations
    parentMap(destination) = origin
  }
  println(s"The number of direct and indirect orbits for the input data is ${countOrbits(inputMap.toMap)}")

  val pathToYOU = calculatePathToPlanet("YOU", parentMap.toMap)
  val pathToSAN = calculatePathToPlanet("SAN", parentMap.toMap)
  println(s"The minimum number of orbital transfers required to move from the object YOU are orbiting to the object " +
    s"SAN are ${countStepsToShortestPath(pathToYOU, pathToSAN)}") // 454

}