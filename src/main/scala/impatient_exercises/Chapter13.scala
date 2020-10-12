// Example: Chapter 13: Collections

package impatient_exercises

import scala.collection.mutable.{LinkedHashSet, ListBuffer}
import scala.collection.mutable

object Chapter13 {

  // **1.Write a function that, given a string, produces a map of the indexes of all characters.**
  //
  //     - For example, indexes("Mississippi") should return a map associating 'M' with the set
  //     {0}, 'i' with the set {1, 4, 7, 10}, and so on. Use a mutable map of characters to mutable
  //     sets. How can you ensure that the set is sorted?

  /**
   * values function does not check for integer overflow
   */
  def indexes(word: String): mutable.Map[Char,LinkedHashSet[Int]] = {
    val wordMap = mutable.Map.empty[Char, LinkedHashSet[Int]]
    for (i <- word.indices; k = word(i)) {
      wordMap(k) = wordMap.getOrElse(k, LinkedHashSet.empty) + i
    }
    wordMap
  }

  // **2.Repeat the preceding exercise, using an immutable map of characters to lists.
  def indexes2(word: String): Map[Char,List[Int]] = {
    val zippedWord = word.zipWithIndex
    zippedWord.foldLeft(Map.empty[Char,List[Int]])( (x,y) =>
      x + (y._1 -> (x.getOrElse(y._1,List.empty) :+ y._2))
    )
  }

  // **3.Write a function that removes every second element from a ListBuffer. Try it two ways.
  //     Call remove(i) for all even i starting at the end of the list. Copy every second element
  //     to a new list. Compare the performance.**
  /*
      ListBuffer is backed by a linkedlist, calling remove method means traversing the list O(n)
      so complexity of removeOdd will be O(n^2)
   */
  def removeOdd(buf: ListBuffer[Any]): ListBuffer[Any] = {
    for(i <- buf.length-1 to 0 by -1) {
      if(i % 2 != 0) buf.remove(i)
    }
    buf
  }

  /*
    removeOdd only need to traverse the list one (O(n) and append desired elements to a new list,
    appending an element to a linked list (ListBuffer) happens in constant time O(1)
  */
  def removeOdd2(buf: ListBuffer[Any]): ListBuffer[Any] = {
    val out = ListBuffer.empty[Any]
    for(i <- 0 to buf.length-1) {
      if(i % 2 == 0) out += buf(i)
    }
    out
  }

}
