package impatient_exercises

import impatient_exercises.Chapter14._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import impatient_exercises.Chapter14.swapArray

class Chapter14Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "swap"

  it should "swap the integer pair" in {
    swap(1,2) shouldEqual (2,1)
  }

  behavior of "Array[Any].swapFirstTwo"

  it should "swap the first two elements of an array" in {
    Array(1,2,3,4,5).swapFirstTwo shouldEqual Array(2,1,3,4,5)
  }



}
