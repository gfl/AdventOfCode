package org.example.adventofcode

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day4Spec extends AnyFlatSpec with Matchers {

  import Day4._

  behavior of "canBePassword"

  it should "return false if the password does not increment" in {
    canBePassword(List(1,1,0)) should be(false)
  }

  it should "return false if the password doesn't have a exactly 2 repetition" in {
    canBePassword(List(1,1,1)) should be(false)
    canBePassword(List(1,2,4)) should be(false)
  }

  it should "return true when all the conditions are met" in {
    canBePassword(List(1,2,2)) should be(true)
  }

  behavior of "passwordGenerator"

  it should "count the possible results between 2 identical numbers" in {
    passwordGenerator(111111, 111111) should be(1)
  }

  it should "count the possible results between 2 consecutive numbers" in {
    passwordGenerator(111111, 111112) should be(2)
  }

  it should "count the possible results between 2 non consecutive numbers" in {
    passwordGenerator(100000, 111113) should be(3)
  }
}
