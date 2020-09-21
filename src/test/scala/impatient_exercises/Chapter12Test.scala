package impatient_exercises

import java.io.ByteArrayOutputStream

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import impatient_exercises.Chapter12.values
import impatient_exercises.Chapter12.largestElement
import impatient_exercises.Chapter12.factorial
import impatient_exercises.Chapter12.factorial2
import impatient_exercises.Chapter12.largest
import impatient_exercises.Chapter12.largestWithParameter
import impatient_exercises.Chapter12.adjustToPair
import impatient_exercises.Chapter12.arrayElementsSizeIs
import impatient_exercises.Chapter12.corresponds
import impatient_exercises.Chapter12.unless

class Chapter12Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "values"

  it should "return a sequence of (i,f(i)) where i is an element in set {low," +
    "range}" in {
    values(x => x*x, 1, 3) should contain only ((1,1),(2,4),(3,9))
  }

  behavior of "largestElement"

  it should "return largest element from specified array" in {
    largestElement(Array(1,10,3,5,999,34567,231,4)) shouldEqual(34567)
  }

  behavior of "factorial"

  it should "compute the factorial" in {
    (0 to 5).map(factorial _) shouldEqual Seq(1, 1, 2, 6, 24, 120)
  }

  behavior of "factorial2"

  it should "compute the factorial" in {
    (0 to 5).map(factorial2 _) shouldEqual Seq(1, 1, 2, 6, 24, 120)
  }

  behavior of "largest"

  it should "return largest value return from applying a function to a range of inputs" in {
    largest(x => 10 * x - x * x, 1 to 10) shouldEqual(25)
  }


  behavior of "largestWithParameter"

  it should "return parameter for which function return is largest when applying a function to a " +
    "range of inputs" in {
    largestWithParameter(x => 10 * x - x * x, 1 to 10) shouldEqual(5)
  }

  behavior of "adjustToPair"

  it should "take a function with two int parameters and apply it to a tuple input" in {
    ((1 to 2) zip (3 to 4)).map(x => adjustToPair(_ * _)(x)) should contain only (3,8)
  }

  behavior of "arrayElementsSizeIs"

  it should "check if sizes of the elements in array correspond to the array of ints input" in {
    arrayElementsSizeIs(Array("cookie","sugar","tea"),Array(6,5,3)) shouldEqual true
  }

  behavior of "corresponds"

  it should "work without currying if types are provided in function literal" in {
    corresponds(
      Array("cookie","sugar","tea"),
      Array(6,5,3),
      (a: String,b: Int) => a.length.equals(b)
    ) shouldEqual true
  }

  behavior of "unless"

  it should "behave like if expression with inverted condition" in {
    val x = 0
    val out = new ByteArrayOutputStream
    Console.withOut(out) {
      unless(x == 1) { println("condition met") }
      unless(x == 0) { println("condition met") }
      out.toString shouldEqual(
        """condition met
          |""".stripMargin)
    }
  }


}
