// Example: Chapter 13: Collections

package impatient_exercises

import scala.collection.mutable.LinkedHashSet
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

}
