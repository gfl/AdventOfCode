package org.example.adventofcode


/**
 * --- Day 4: Secure Container ---
 * You arrive at the Venus fuel depot only to discover it's protected by a password. The Elves had written the password on a sticky note, but someone threw it out.
 *
 * However, they do remember a few key facts about the password:
 *
 * It is a six-digit number.
 * The value is within the range given in your puzzle input.
 * Two adjacent digits are the same (like 22 in 122345).
 * Going from left to right, the digits never decrease; they only ever increase or stay the same (like 111123 or 135679).
 * Other than the range rule, the following are true:
 *
 * 111111 meets these criteria (double 11, never decreases).
 * 223450 does not meet these criteria (decreasing pair of digits 50).
 * 123789 does not meet these criteria (no double).
 * How many different passwords within the range given in your puzzle input meet these criteria?
 */
object Day4 extends App {

  def canBePassword(input: List[Int]): Boolean = {
    input.equals(input.sorted) && input.groupBy(identity).view.mapValues(_.size).exists(_._2 == 2)
  }

  def passwordGenerator(startNumber: Int, endNumber: Int): Int = {
    def pwgen(prefixes: List[Int], start: List[Int], end: List[Int], count: Int): Int = {
      if (prefixes.size == 6) {
        if (canBePassword(prefixes)) {
          return count + 1
        } else {
          return count
        }
      } else {
        val s = start.head
        val e = end.head
        return (s to e).foldLeft[Int](count)(
          (acc, x) => {
            val nextEnd = if (x == e) {
              end.tail
            } else {
              List.fill(6 - prefixes.length)(9)
            }
            val nextStart = if (x == s) {
              start.tail
            } else {
              List.fill(6 - prefixes.length)(s)
            }
            pwgen(prefixes ++ List(x), nextStart, nextEnd, acc)
          }
        )
      }
    }

    val sl = startNumber.toString.split("").toList.map(_.toInt)
    val el = endNumber.toString.split("").toList.map(_.toInt)
    pwgen(Nil, sl, el, 0)
  }

  val start = 347312
  val end = 805915
  println(s"The number of different passwords between $start and $end is: ${passwordGenerator(start, end)}")

}

