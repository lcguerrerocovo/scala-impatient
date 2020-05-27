package impatient_exercises

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.util.Random
import scala.math._

class Chapter02Test extends AnyFlatSpec with Matchers {
  behavior of "function that computes x^n"

  it should "equal y^2 where y = x^(n/2)" in {
    Chapter02.power(2,4) shouldEqual pow(pow(2,4/2),2)
  }

  it should "equal x*x(n-1) if n is odd and positive" in {
    Chapter02.power(2,3) shouldEqual (2 * 2 * 2)
  }

  it should "equal 1 if n is 0" in {
    Chapter02.power(Random.nextInt,0) shouldEqual 1
  }

  it should "equal 1 / x ^(-n) if n is negative" in {
    Chapter02.power(2,-3) shouldEqual (.5 * .5 * .5)
  }
}
