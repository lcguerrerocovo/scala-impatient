package impatient_exercises

import scala.collection.mutable.ArrayBuffer

object Main extends App {

  println("Chapter 1 exercises")

  println("2. In the Scala REPL, compute the square root of 3, and then square that value. By how much does the result differ from 3? (Hint: The res variables are your friend.)")
  println("> " + Chapter01.floatingPointDiff)
  println("4. Scala lets you multiply a string with a number—try out \"crazy\" * 3 in the REPL" +
    ".\nWhat does this operation do? Where can you find it in Scaladoc?")
  println("> " + Chapter01.multiplyString)
  println("-- prints out the string n times")
  println("5. What does 10 max 2 mean? In which class is the max method defined?")
  println("> " + Chapter01.maxOfInts)
  println("-- returns max of two integers, max method is defined in scala.runtime.RichInt")
  println("6. Using BigInt, compute 21024.")
  println("> " + Chapter01.bigIntPower)
  println("7. What do you need to import so that you can get a random prime as probablePrime(100, Random), without any qualifiers before probablePrime and Random?")
  println("-- import scala.util.Random & import scala.BigInt._")
  println("8. One way to create ran dom file or directory names is to produce a random BigInt and convert it to base 36, yielding a string such as \"qsnvbevtomcj38o06kul\". Poke around Scaladoc to find a way of doing this in Scala.")
  println("> " + Chapter01.randomFileName)
  println("9. How do you get the first character of a string in Scala? The last character?")
  println("> " + Chapter01.firstAndLast)
  println("10. What do the take, drop, takeRight, and dropRight string functions do? What advantage or disadvantage do they have over using substring?")
  println("-- with substring the indexes have to be within the bounds of the string size or an " +
    "'IndexOutOfBoundsException' exception is thrown")

  println("Chapter 3 Arrays - exercises")

  println("1. Write a code snippet that sets a to an array of n random integers between 0 (inclusive) and n (exclusive).")
  println(Chapter03.randomArray(10).mkString(","))
  println("2. Write a loop that swaps adjacent elements of an array of integers. For example, Array(1, 2, 3, 4, 5) becomes Array(2, 1, 4, 3, 5).")
  println(Chapter03.swapAdjacent(Array(1, 2, 3, 4, 5)).mkString(","))
  println("3. Repeat the preceding assignment, but produce a new array with the swapped values. Use for/yield.")
  println(Chapter03.swapAdjacentYield(Array(1, 2, 3, 4, 5)).mkString(","))
  println("4. Given an array of integers, produce a new array that contains all positive values of the original array, in their original order, followed by all values that are zero or negative, in their original order.")
  println(Chapter03.positivesFirst(Array(-1, 1, 0, 2, 3)).mkString(","))
  println("5. How do you compute the average of an Array[Double]?")
  println(Chapter03.arrAvg(Array(456.0, 34.15, 1, -45, 2)))
  println("6. 6. How do you rearrange the elements of an Array[Int] so that they appear in\nreverse sorted order? How do you do the same with an ArrayBuffer[Int]?")
  println(Chapter03.reverseSortArray(Array(456, 34, 1, -45, 2)).mkString(","))
  println(Chapter03.reverseSortArrayBuffer(ArrayBuffer(456, 34, 1, -45, 2)).mkString(","))
  println("-- user sortWith and provide a comparator function, do the same with ArrayBuffer")
  println("7. Write a code snippet that produces all values from an array with duplicates\nremoved. (Hint: Look at Scaladoc.)")
  println("-- user distinct function")
  println("8. Rewrite the example at the end of Section 3.4, “Transforming Arrays,” on page 34 using the drop method for dropping the index of the first match. Look the method up in Scaladoc.")
  println(Chapter03.fropFirstNegative(Array(456, 34, 1, -45, 2)).mkString(","))
  println("9. Make a collection of all time zones returned by java.util.TimeZone.getAvailableIDs that are in America. Strip off the \"America/\" prefix and sort the result.")
  println(Chapter03.sortedAmericanTimeZones.mkString(","))
  println("10. get a scala buffer from some java call")
  println("-- just add .asScala with proper imports to get scala buffer back")


  println("Chapter 4 Maps - exercises")
  /*print(
    """
      |2. Write a program that reads words from a file. Use a mutable map to count how often each word appears. To read the words, simply use a java.util.Scanner:
      |val in = new java.util.Scanner(java.io.File("myfile.txt")) while (in.hasNext()) process in.next()
      |""".stripMargin)
  println(Chapter04.readFileCountWordsMutable("words.txt")(Chapter04.scalaMap))
  println("3. Repeat the preceding exercise with an immutable map.")
  println(Chapter04.readFileCountWordsImmutable("words.txt")(Chapter04.unsortedMap))
  println("4. Repeat the preceding exercise with a sorted map, so that the words are printed in " +
    "sorted order.")
  println(Chapter04.readFileCountWordsImmutable("words.txt")(Chapter04.sortedMap))
  println("5. Repeat the preceding exercise with a java.util.TreeMap that you adapt to the Scala API.")
  println(Chapter04.readFileCountWordsMutable("words.txt")(Chapter04.javaMap))*/
  println("6. Define a linked hash map that maps \"Monday\" to java.util.Calendar.MONDAY, and similarly for the other weekdays. Demonstrate that the elements are visited in insertion order.")
  println("-- checked in Chapter4Test")
  println(
    """
      |7. Print a table of all Java properties
      |You need to find the length of the longest key before you can print the table.
      |""".stripMargin)
  println(Chapter04.printProperties.mkString("\n"))
  println("8. Write a function minmax(values: Array[Int]) that returns a pair containing the smallest and largest values in the array.")
  println(Chapter04.minMax(Array(34,-1234,3535,4,0,1)))
  println("9. Write a function lteqgt(values: Array[Int], v: Int) that returns a triple containing the counts of values less than v, equal to v, and greater than v.")
  println(Chapter04.lteqgt(Array(34,-1234,3535,4,0,1),4))
  println("10. What happens when you zip together two strings,such as \"Hello\".zip(\"World\")? " +
    "Come up with a plausible use case.")
  println("-- the string is zipped element by element giving a sequence of tuples with " +
    "corresponding elements, this is useful if wanting to compare characters by position")



}
