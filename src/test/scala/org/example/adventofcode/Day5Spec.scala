package org.example.adventofcode

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day5Spec extends AnyFlatSpec with Matchers {

  import Day5._

  behavior of "extractOperationInfo"

  it should "extract the operation code when it is the only thing provided" in {
    extractOperationInfo(1) should be(OperationInfo(1))
    extractOperationInfo(99) should be(OperationInfo(99))
  }

  it should "extract the operation code and the param access modes" in {
    extractOperationInfo(1002) should be(OperationInfo(2, 0, 1))
  }

  behavior of "getValueBasedOnAccessMode"

  it should "get the value from the position when in position mode" in {
    getValueBasedOnAccessMode(List(11, 22, 33, 44, 55), 3, POSITION_MODE) should be(44)
  }

  it should "get the value from the position when in immediate mode" in {
    getValueBasedOnAccessMode(List(11, 22, 33, 44, 55), 3, IMMEDIATE_MODE) should be(3)
  }


  behavior of "runIncodeProgram"

  class TestComputer(memory: List[Int], input: Int) extends Computer(memory) {

    var output: Option[Int] = None

    override def readInput(): Int = {
      input
    }

    override def printOutput(output: Int): Unit = {
      this.output = Some(output)
    }
  }

  it should "output 1 when the input is equal to 8 in position mode" in {
    val computer = new TestComputer(List(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8), input = 8)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(1)
  }

  it should "output 0 when the input is not equal to 8 in position mode" in {
    val computer = new TestComputer(List(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8), input = 6)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(0)
  }

  it should "output 1 when input is less than 8 in position mode" in {
    val computer = new TestComputer(List(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8), input = 5)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(1)
  }

  it should "output 0 when input is not less than 8 in position mode" in {
    val computer = new TestComputer(List(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8), input = 15)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(0)
  }

  it should "output 1 when the input is equal to 8 in immediate mode" in {
    val computer = new TestComputer(List(3, 3, 1108, -1, 8, 3, 4, 3, 99), input = 8)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(1)
  }

  it should "output 0 when the input is not equal to 8 in immediate mode" in {
    val computer = new TestComputer(List(3, 3, 1108, -1, 8, 3, 4, 3, 99), input = 6)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(0)
  }

  it should "output 1 when input is less than 8 in immediate mode" in {
    val computer = new TestComputer(List(3, 3, 1107, -1, 8, 3, 4, 3, 99), input = 5)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(1)
  }

  it should "output 0 when input is not less than 8 in immediate mode" in {
    val computer = new TestComputer(List(3, 3, 1107, -1, 8, 3, 4, 3, 99), input = 15)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(0)
  }

  it should "output 0 when running a jump instruction in position mode" in {
    val computer = new TestComputer(List(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9), input = 0)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(0)
  }

  it should "output 1 when running a jump instruction in position mode" in {
    val computer = new TestComputer(List(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9), input = 11)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(1)
  }

  it should "output 0 when running a jump instruction in immediate mode" in {
    val computer = new TestComputer(List(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1), input = 0)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(0)
  }

  it should "output 1 when running a jump instruction in immediate mode" in {
    val computer = new TestComputer(List(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1), input = 1)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(1)
  }

  it should "output 999 with a larger example when the input is below 8" in {
    val computer = new TestComputer(List(3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0,
      36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1,
      46, 98, 99), input = 5)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(999)
  }

  it should "output 1000 with a larger example when the input is 8" in {
    val computer = new TestComputer(List(3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0,
      36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1,
      46, 98, 99), input = 8)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(1000)
  }

  it should "output 1001 with a larger example when the input is greater than 8" in {
    val computer = new TestComputer(List(3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0,
      36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1,
      46, 98, 99), input = 10)
    computer.runIncodeProgram()
    computer.output shouldBe defined
    computer.output.get should be(1001)
  }

}
