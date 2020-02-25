package org.example.adventofcode

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day2Spec extends AnyFlatSpec with Matchers {

  import Day2.Computer

  behavior of "runIncodeProgram"

  it should "produce 2,0,0,0,99 from 1,0,0,0,99 (1 + 1 = 2)" in {
    val computer = Computer(List(1, 0, 0, 0, 99))
    computer.runIncodeProgram()
    computer.memory should be(List(2, 0, 0, 0, 99))
  }

  it should "produce 2,3,0,6,99 from 2,3,0,3,99 (3 * 2 = 6)" in {
    val computer = Computer(List(2,3,0,3,99))
    computer.runIncodeProgram()
    computer.memory should be(List(2,3,0,6,99))
  }

  it should "produce 2,4,4,5,99,9801 from 2,4,4,5,99,0 (99 * 99 = 9801)" in {
    val computer = Computer(List(2,4,4,5,99,9801))
    computer.runIncodeProgram()
    computer.memory should be(List(2,4,4,5,99,9801))
  }

}
