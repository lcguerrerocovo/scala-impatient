package impatient_exercises

import impatient_exercises.Chapter11.Fraction
import org.scalacheck.Gen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class Chapter11Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  val Tolerance = 1.0E-4

  def withIntGenerator(testCode: (Int, Int, Int, Int) => Any): Unit = {
    val anyInt = Gen.choose(-10000, 10000)
    forAll(anyInt, anyInt, anyInt, anyInt) {
      (a: Int, b: Int, c: Int, d: Int) =>
        whenever(
          b != 0 && d != 0
            && a != Integer.MIN_VALUE && b != Integer.MIN_VALUE
            && c != Integer.MIN_VALUE && d != Integer.MIN_VALUE
        ) {
          testCode(a, b, c, d)
        }
    }
  }

  behavior of "Fraction"

  it should "add fractions" in withIntGenerator {
    (a: Int, b: Int, c: Int, d: Int) =>
      val result = a / b.toDouble + c / d.toDouble
      val fraction = new Fraction(a, b) + new Fraction(c, d)
      fraction.toDouble shouldEqual result +- Tolerance
  }

  it should "add fractions and normalize them" in {
    new Fraction(10,-6) + new Fraction(5,-6) shouldEqual new Fraction(-5, 2)
  }

  it should "subtract fractions" in withIntGenerator {
    (a: Int, b: Int, c: Int, d: Int) =>
      val result = a / b.toDouble - c / d.toDouble
      val fraction = new Fraction(a, b) - new Fraction(c, d)
      fraction.toDouble shouldEqual result +- Tolerance
  }

  it should "subtract fractions and normalize them" in {
    new Fraction(20,-6) - new Fraction(5,-6) shouldEqual new Fraction(-5, 2)
  }

  it should "multiply fractions" in withIntGenerator {
    (a: Int, b: Int, c: Int, d: Int) =>
      val result = a / b.toDouble * c / d.toDouble
      val fraction = new Fraction(a, b) * new Fraction(c, d)
      fraction.toDouble shouldEqual result +- Tolerance
  }

  it should "multiply fractions and normalize them" in {
    new Fraction(20,-6) * new Fraction(5,-6) shouldEqual new Fraction(25, 9)
  }

  it should "divide fractions" in withIntGenerator {
    (a: Int, b: Int, c: Int, d: Int) =>
      val result = (a / b.toDouble) / (c / d.toDouble)
      val fraction = new Fraction(a, b) / new Fraction(c, d)
      fraction.toDouble shouldEqual result +- Tolerance
  }

  it should "divide fractions and normalize them" in {
    new Fraction(20,-2) / new Fraction(3,-4) shouldEqual new Fraction(40, 3)
  }

}
