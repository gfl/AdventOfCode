package org.example.adventofcode

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Day1Spec extends AnyFlatSpec with Matchers {

  import Day1._

  behavior of "calculate_required_fuel"

  it should "return 2 for a mass of 12" in {
    calculate_required_fuel(12) should be(2)
  }

  it should "return 2 for a mass of 14" in {
    calculate_required_fuel(14) should be(2)
  }

  it should "return 966 for a mass of 1969" in {
    calculate_required_fuel(1969) should be(966)
  }

  it should "return 50346 for a mass of 100756" in {
    calculate_required_fuel(100756) should be(50346)
  }


}
