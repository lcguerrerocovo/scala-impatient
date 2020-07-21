package impatient_exercises

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.util.Random
import scala.math._
import org.scalacheck.Gen
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class Chapter02Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "signum"

  it should "return a 1 if the number is positive" in {
    forAll (Gen.posNum[Int]) { x: Int =>
      Chapter02.signum(x) shouldEqual 1
    }
  }

  it should "return a -1 if it is negative" in {
    forAll (Gen.negNum[Int]) { x: Int =>
      Chapter02.signum(x) shouldEqual -1
    }
  }

  it should "return a 0 if it is zero" in {
    Chapter02.signum(0) shouldEqual 0
  }

  behavior of "unicodeProduct"

  it should "return the unicode product of a string" in {
    Chapter02.unicodeProduct("Hello") shouldEqual 825152896
  }

  behavior of "unicodeProduct2"

  it should "return the unicode product of a string" in {
    Chapter02.unicodeProduct2("Hello") shouldEqual 825152896
  }

  behavior of "product"

  it should "return the unicode product of a string" in {
    Chapter02.product("Hello") shouldEqual 825152896
  }

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
