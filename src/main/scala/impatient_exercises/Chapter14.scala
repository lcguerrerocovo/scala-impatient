// Example: Chapter 14: Pattern Matching and Case Classes

package impatient_exercises

import scala.reflect.ClassTag

object Chapter14 {

  // **2.Using pattern matching, write a function swap that receives a pair of integers and
  //     returns the pair with the components swapped.**
  def swap(pair: (Int,Int)): (Int, Int) = pair match { case (a, b) => (b,a) }

  // **3.Using pattern matching, write a function swap that swaps the first two elements of an
  //     array provided its length is at least two.**
  implicit def swapArray[T:ClassTag](arr: Array[T]): ArrayWithSwap[T] = new ArrayWithSwap[T](arr)

  class ArrayWithSwap[T:ClassTag](arr: Array[T]) {
    def swapFirstTwo: Array[T] = arr match {
      case Array(a,b, rest @ _*) if arr.length >= 2 => {
        Array(b,a) ++ rest.toArray[T]
      }
      case _ => arr
    }
  }

  // **4.Add a case class Multiple that is a subclass of the Item class.**
  //
  //     - For example:
  //     - `Multiple(10, Article("Blackwell Toaster", 29.95))`
  //     - describes ten toasters.
  //
  //     **Of course, you should be able to handle any items, such as bundles or multiples, in the
  //     second argument. Extend the price function to handle this new case.**
  abstract class Item
  case class Article(description: String, price: Double) extends Item
  case class Bundle(description: String, discount: Double, items: Item*) extends Item
  case class Multiple(quantity: Int, item: Item) extends Item

  def price(it: Item): Double = it match {
    case Article(_, p) => p
    case Bundle(_, disc, its @ _*) =>
      its.map {
        case b: Bundle => price(b)
        case Article(_,price) => price
        case Multiple(quantity, item) => quantity * price(item)
      }.sum - disc
    case Multiple(quantity, item) => quantity * price(item)
  }

  // **5.One can use lists to model trees that store values only in the leaves. For example,
  //     the list ((3 8) 2 (5)) describes the tree ->**

  //   **However, some of the list elements are numbers and others are lists. In Scala, you cannot
  //     have heterogeneous lists, so you have to use a List[Any]. Write a leafSum function to
  //     compute the sum of all elements in the leaves, using pattern matching to differentiate
  //     between numbers and lists.**
  /**
   *      •
   *     /|\
   *    • 2 •
   *   /\   |
   *  3  8  5
   */
  def leafSum(tree: List[Any]): Int = tree match {
    case Nil => 0
    case (head: Int) :: tail => head + leafSum(tail)
    case (head: List[Any]) :: tail => leafSum(head) + leafSum(tail)
  }

  // **6.A better way of modeling such trees is with case classes. Let’s start with binary trees.
  //     Write a function to compute the sum of all elements in the leaves.**
  sealed abstract class Tree
  case class Leaf(value: Int) extends Tree
  case class Node(child: Tree*) extends Tree
  case class NodeOp(op: Operator, child: Tree*) extends Tree


  def leafSum(tree: Tree): Int = tree match {
    case Leaf(value) => value
    case Node(left, right) => leafSum(left) + leafSum(right)
  }

  // **7.Extend the tree in the preceding exercise so that each node can have an arbitrary
  //     number of children, and reimplement the leafSum function. The tree in Exercise 5 should
  //     be expressible as:**
  //
  //     - `Node(Node(Leaf(3), Leaf(8)), Leaf(2), Node(Leaf(5)))`
  def leafSum2(tree: Tree): Int = tree match {
    case Leaf(value) => value
    case Node(nodes @ _*) => nodes.map(leafSum2(_)).sum
  }

  // **8.Extend the tree in the preceding exercise so that each non leaf node stores an operator
  //     in addition to the child nodes. Then write a function eval that computes the value. For
  //     example, the tree ->**
  //
  //     - Pay attention to the unary minus.
  /**
   *      +
   *     /|\
   *    * 2 -
   *   /\   |
   *  3 8  5
   *
   * has value (3 * 8) + 2 + (–5) = 21.
   */
  sealed abstract class Operator
  case object - extends Operator
  case object + extends Operator
  case object / extends Operator
  case object * extends Operator

  def leaves(tree: Tree): Int = tree match {
    case Leaf(value) => value
    case NodeOp(op, nodes @ _*) if nodes.size == 1 => op match {
      case - => -leaves(nodes.head)
      case _ => leaves(nodes.head)
    }
    case NodeOp(op, nodes @ _*) => op match {
      case - => nodes.map(leaves(_)).reduce(_ - _)
      case + => nodes.map(leaves(_)).reduce(_ + _)
      case / => nodes.map(leaves(_)).reduce(_ / _)
      case * => nodes.map(leaves(_)).reduce(_ * _)
    }
    case Node(nodes @ _*) => nodes.map(leafSum2(_)).sum
  }

  // **9.Write a function that computes the sum of the non-None values in a
  //     List[Option[Int]]. Don’t use a match statement.**
  def sum(lst: List[Option[Int]]): Int = lst.flatten.sum

  // **10.Write a function that composes two functions of type Double => Option[Double], yielding
  //      another function of the same type. The composition should yield None if either function
  //      does.**
  //      - For example ->
  //      - Then h(2) is Some(1), and h(1) and h(0) are None.
  /**
   *      def f(x: Double) = if (x != 1) Some(1 / (x - 1)) else None
   *      def g(x: Double) = if (x >= 0) Some(sqrt(x)) else None
   *      val h = compose(g, f) // h(x) should be g(f(x))
   */
  def compose(g: Double => Option[Double], f: Double => Option[Double])(x: Double)
  : Option[Double] = {
    f(x) match {
      case Some(y) => g(y)
      case _ => None
    }
  }
}
