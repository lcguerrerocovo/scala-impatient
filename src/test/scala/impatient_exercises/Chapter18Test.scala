package impatient_exercises

import impatient_exercises.Chapter18._
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class Chapter18Test extends AsyncFlatSpec with Matchers {

  behavior of "Pair"

  it should "swap the two elements in the pair producing a new pair (immutable)" in {
    Pair(1,2d).swap shouldEqual Pair(2d,1)
  }

  behavior of "Pair2"

  it should "swap the two elements in the pair producing a new pair (mutable)" in {
    val firstPair = Pair2(1,2d)
    val secondPair = Pair2(2d,1)
    firstPair.swap shouldEqual secondPair
    assert(firstPair.swap === firstPair)
  }

  behavior of "swap"

  it should "swap the two elements in the pair producing a new pair" in {
    swap(Pair(1,2d)) shouldEqual Pair(2d,1)
  }

  behavior of "middle"

  it should "return the middle element of any Iterable"in {
    middle(List[Int]()) shouldEqual None
    middle(List(1)) shouldEqual Some(1)
    middle(List(1,2,3,4,5)) shouldEqual Some(3)
    middle("World") shouldEqual Some('r')
  }



}
