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

  behavior of "leafSum(tree: List[Any]): Int"

  it should "add all leaves in list representation of a binary tree" in {
    leafSum(List(List(3,8), 2, List(5))) shouldEqual 18
    leafSum(List(List(3,8,2), 2, List(5))) shouldEqual 20
  }

  behavior of "leafSum(tree: BinaryTree): Int"

  it should "add all leaves in list representation of a binary tree" in {
    leafSum(Node(Node(Leaf(2),Leaf(8)),Node(Leaf(5),Leaf(5)))) shouldEqual 20
  }

  behavior of "leafSum2(tree: BinaryTree): Int"

  it should "add all leaves in list representation of a binary tree" in {
    leafSum2(Node(Node(Leaf(3), Leaf(8)), Leaf(2), Node(Leaf(5)))) shouldEqual 18
  }

  behavior of "leaves"

  it should "should apply operator in each node to leaves computing a result" in {
    leaves(NodeOp(+,
      NodeOp(*,Leaf(3), Leaf(8)), Leaf(2), NodeOp(-,Leaf(5)))) shouldEqual 21
  }

  behavior of "sum"

  it should "sum all non-None in a list" in {
    sum(List(Some(4), None, Some(13), None, Some(1))) shouldEqual  18
  }

  behavior of "compose"

  it should "compose two functions and return None if either of them returns None" in {
    def f(x: Double): Option[Double] = if (x != 1) Some(1 / (x - 1)) else None
    def g(x: Double): Option[Double] = if (x >= 0) Some(math.sqrt(x)) else None
    val h = compose(g,f) _
    h(1) shouldEqual None
    h(0) shouldEqual None
    h(2) shouldEqual Some(1)
    h(5) shouldEqual Some(0.5d)
  }




}
