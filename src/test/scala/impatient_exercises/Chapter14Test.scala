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

  it should "swap the first two elements of an array of Int" in {
    Array(1,2,3,4,5).swapFirstTwo shouldEqual Array(2,1,3,4,5)
  }

  it should "swap the first two elements of an array of String" in {
    Array("a","b","c","d","e").swapFirstTwo shouldEqual Array("b","a","c","d","e")
  }

  behavior of "Multiple"

  val m1 = Multiple(10, Article("Blackwell Toaster", 29.95))
  val m2 = Multiple(10,
    Bundle("breakfast bundle", 15,
      Article("toaster",30), Article("blender", 45)
    )
  )
  val m3 = Multiple(10, m1)
  val b1 = Bundle("super bundle", 1000, m1, m2, m3)

  it should "compute price for a Multiple with an Article Item" in {
    price(m1) shouldEqual 299.5d
  }

  it should "compute price for a Multiple with a Bundle Item" in {
    price(m2) shouldEqual 600d
  }

  it should "compute price for a Multiple with a Multiple item" in {
    price(m3) shouldEqual 2995
  }

  it should "compute price for a Bundle with all multiple items" in {
    price(b1) shouldEqual 2894.5
  }

  behavior of "leafSum"

  it should "add all leaves in list representation of a binary tree" in {
    leafSum(List(List(3,8), 2, List(5))) shouldEqual 18
  }



}
