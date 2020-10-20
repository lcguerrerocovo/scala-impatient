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
  class ArrayWithSwap[T:ClassTag](arr: Array[T]) {
    def swapFirstTwo: Array[T] = arr match {
      case Array(a,b, rest @ _*) if arr.length >= 2 => {
        val buff = new ArrayBuffer[T]()
        ((buff += b += a) ++ rest.toArray).toArray
      }
      case _ => arr
    }
  }

  implicit def swapArray[T:ClassTag](arr: Array[T]): ArrayWithSwap[T] = new ArrayWithSwap[T](arr)

}
