package impatient_exercises

import impatient_exercises.Chapter13._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

import scala.collection.mutable.ListBuffer

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
    indexes2("Mississippi")('M') should contain only  0
    indexes2("Mississippi")('i') should contain inOrder (1, 4, 7, 10)
    indexes2("Mississippi")('s') should contain inOrder (2,3,5,6)
    indexes2("Mississippi")('p') should contain inOrder (8,9)
  }

  behavior of "removeOdd"

  it should "remove every second element of a ListBuffer using remove()" in {
    removeOdd(ListBuffer('t','e','s','t','i','n','g')) shouldEqual ListBuffer('t','s','i','g')
  }

  behavior of "removeOdd2"

  it should "remove every second element of a ListBuffer appending to a new list" in {
    removeOdd2(ListBuffer('t','e','s','t','i','n','g')) shouldEqual ListBuffer('t','s','i','g')
  }

  it should "removeOdd2 be faster than removeOdd" in {
    utils.time { removeOdd(ListBuffer('t','e','s','t','i','n','g')) }("removeOdd") >
      utils.time {removeOdd2(ListBuffer('t','e','s','t','i','n','g')) }("removeOdd2")
  }


}
