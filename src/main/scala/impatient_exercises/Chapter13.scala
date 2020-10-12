// Example: Chapter 13: Collections

package impatient_exercises

import scala.collection.mutable.{ArrayBuffer, LinkedHashSet, ListBuffer}
import scala.collection.mutable

object Chapter13 {

  // **1.Write a function that, given a string, produces a map of the indexes of all characters.**
  //
  //     - For example, indexes("Mississippi") should return a map associating 'M' with the set
  //     {0}, 'i' with the set {1, 4, 7, 10}, and so on. Use a mutable map of characters to mutable
  //     sets. How can you ensure that the set is sorted?
  def indexes(word: String): mutable.Map[Char,LinkedHashSet[Int]] = {
    val wordMap = mutable.Map.empty[Char, LinkedHashSet[Int]]
    for (i <- word.indices; k = word(i)) {
      wordMap(k) = wordMap.getOrElse(k, LinkedHashSet.empty) + i
    }
    wordMap
  }

  // **2.Repeat the preceding exercise, using an immutable map of characters to lists.**
  def indexes2(word: String): Map[Char,List[Int]] = {
    val zippedWord = word.zipWithIndex
    zippedWord.foldLeft(Map.empty[Char,List[Int]])( (x,y) =>
      x + (y._1 -> (x.getOrElse(y._1,List.empty) :+ y._2))
    )
  }

  // **3.Write a function that removes every second element from a ListBuffer. Try it two ways.
  //     Call remove(i) for all even i starting at the end of the list. Copy every second element
  //     to a new list. Compare the performance.**
  /**
   * ListBuffer is backed by a linkedlist, calling remove method means traversing the list O(n)
   * so complexity of removeOdd will be O(n^2)
   */
  def removeOdd(buf: ListBuffer[Any]): ListBuffer[Any] = {
    for(i <- buf.length-1 to 0 by -1) {
      if(i % 2 != 0) buf.remove(i)
    }
    buf
  }

  /**
   * removeOdd2 only need to traverse the list one (O(n) and append desired elements to a new list,
   * appending an element to a linked list (ListBuffer) happens in constant time O(1)
   */
  def removeOdd2(buf: ListBuffer[Any]): ListBuffer[Any] = {
    val out = ListBuffer.empty[Any]
    for(i <- 0 to buf.length-1) {
      if(i % 2 == 0) out += buf(i)
    }
    out
  }

  // **4.Write a function that receives a collection of strings and a map from strings to
  // integers. Return a collection of integers that are values of the map corresponding
  // to one of the strings in the collection. For example, given Array("Tom", "Fred", "Harry")
  // and Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5), return Array(3, 5).**
  //    - Hint: Use flatMap to combine the Option values returned by get.
  def simple(keys: Seq[String], m: Map[String,Int]): Seq[Int] = {
    keys.flatMap(x => m.get(x))
  }

  // **5.Implement a function that works just like mkString, using reduceLeft.**
  def mkString(coll: Seq[Any], sep: String) = {
    coll.reduceLeft(_.toString + sep + _.toString)
  }

  // **6.Given a list of integers lst, what is (lst :\ List[Int]())(_ :: _)? (List[Int]() /:
  //     lst)(_ :+ _)? How can you modify one of them to reverse the list?**
  //
  //     - both operations give back the same list lst
  //     - (List[Int]() /: lst)((x,y) => y :: x) reverses the list

  // **7.In Section 13.10, “Zipping,” on page 187, the expression
  //        (prices zip quantities) map { p => p._1 * p._2 }
  //     is a bit inelegant. We can’t do
  //        (prices zip quantities) map {_*_}
  //     because _*_ is a function with two arguments, and we need a function with one argument
  //     that is a tuple. The tupled method of the Function object changes a function with two
  //     arguments to one that takes a tuple. Apply tupled to the multiplication function so you
  //     can map it over the list of pairs.**
  def zipMap(prices: List[Double], quantities: List[Int], f: (Double,Int) => Double)
  : List[Double] = {
    (prices zip quantities) map { f.tupled(_) }
  }

  // ** 8.Write a function that turns an array of Double values into a two-dimensional array. Pass
  //      the number of columns as a parameter. For example, with Array(1, 2, 3, 4, 5, 6) and three
  //      columns, return Array(Array(1, 2, 3), Array(4, 5, 6)). Use the grouped method.
  def array2D(arr: Array[Double]): Array[Array[Double]] = {
    val buf = ArrayBuffer[Array[Double]]()
    for(e <- arr.grouped(arr.length / 2)) buf += e
    buf.toArray
  }

}
