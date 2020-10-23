package impatient_exercises

import impatient_exercises.Chapter18._
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class Chapter18Test extends AsyncFlatSpec with Matchers {

  behavior of "Pair"

  it should "swap the two elements in the pair producting a new pair" in {
    Pair(1,2d).swap shouldEqual Pair(2d,1)
  }

  behavior of "Pair2"

  it should "swap the two elements in the pair producting a new pair" in {
    Pair2(1,2d).swap shouldEqual Pair2(2d,1)
  }


}
