// Example: Chapter 14: Pattern Matching and Case Classes

package impatient_exercises

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, LinkedHashSet, ListBuffer}
import scala.collection.parallel._
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

  // **4.Add a case class Multiple that is a subclass of the Item class.
  //     - For example:
  //        `Multiple(10, Article("Blackwell Toaster", 29.95))`
  //       describes ten toasters.
  //     Of course, you should be able to handle any items, such as bundles or multiples, in the
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
  //     the list ((3 8) 2 (5)) describes the tree
  //          •
  //         /|\
  //        • 2 •
  //       /\   |
  //      3  8  5
  //     However, some of the list elements are numbers and others are lists. In Scala, you cannot
  //     have heterogeneous lists, so you have to use a List[Any]. Write a leafSum function to
  //     compute the sum of all elements in the leaves, using pattern matching to differentiate
  //     between numbers and lists.**
  def leafSum(tree: List[Any]): Int = tree match {
    case Nil => 0
    case (head: Int) :: tail => head + leafSum(tail)
    case (head: List[Any]) :: tail => leafSum(head) + leafSum(tail)
  }
}
