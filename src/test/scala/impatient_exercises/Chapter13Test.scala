package impatient_exercises

import impatient_exercises.Chapter13._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class Chapter13Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "indexes"

  it should "given a string, produces a map of sorted indexes of all characters" in {
    indexes("Mississippi")('M').toList should contain only  0
    indexes("Mississippi")('i').toList should contain inOrder (1, 4, 7, 10)
    indexes("Mississippi")('s').toList should contain inOrder (2,3,5,6)
    indexes("Mississippi")('p').toList should contain inOrder (8,9)
  }

  behavior of "indexes2"

  it should "given a string, produces a map of sorted indexes of all characters" in {
    indexes2("Mississippi")('M').toList should contain only  0
    indexes2("Mississippi")('i').toList should contain inOrder (1, 4, 7, 10)
    indexes2("Mississippi")('s').toList should contain inOrder (2,3,5,6)
    indexes2("Mississippi")('p').toList should contain inOrder (8,9)
  }


}
