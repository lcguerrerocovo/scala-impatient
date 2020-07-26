package impatient_exercises

import impatient_exercises.Chapter10.RectangleLike
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class Chapter10Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "RectangleLike"

  it should "provide concrete implementations for translate" in  {
    val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
    egg.translate(10,-10)

    egg.x shouldEqual 15.0
    egg.y shouldEqual 0.0
    egg.width shouldEqual 20.0
    egg.height shouldEqual 30.0
  }

  it should "provide concrete implementations for grow" in  {
    val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
    egg.grow(10,20)

    egg.x shouldEqual -5.0
    egg.y shouldEqual -10.0
    egg.width shouldEqual 40.0
    egg.height shouldEqual 70.0
  }




}
