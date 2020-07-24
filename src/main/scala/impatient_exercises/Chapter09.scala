// Example: Chapter 9: Files and Regular Expressions

package impatient_exercises

import java.io.{FileWriter, PrintWriter}

import impatient_exercises.Chapter08.Point

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object Chapter09 {

  // **1.Write a Scala code snippet that reverses the lines in a file (making the last line the
  //   first one, and so on).**
  def reverseFile(path: String): Unit  = {
    val reversedContents = Source.fromFile(path).getLines.toArray.reverse.mkString("\n")
    new PrintWriter(path) {
      write(reversedContents)
      close()
    }
  }

  // **2.Write a Scala program that reads a file with tabs, replaces each tab with spaces so that
  //   tab stops are at n-column boundaries, and writes the result to the same file.**
  def tabsToSpaces(path: String, spaces: Int): Unit  = {
    val contents = Source.fromFile(path)
      .mkString
      .replaceAll("\t", (" " * spaces))

    new PrintWriter(path) {
      write(contents)
      close()
    }
  }

  // **3.Write a Scala code snippet that reads a file and prints all words with more than 12
  // characters to the console. Extra credit if you can do this in a single line.**
  def printWords(path: String, minimumWordSize: Int) = {
    Source.fromFile(path)
      .mkString.split("\\s+")
      .filter(_.length > minimumWordSize)
      .foreach(println)
  }

  // **4.Write a Scala program that reads a text file containing only floating-point numbers.
  // Print the sum, average, maximum, and minimum of the numbers in the file.**
  def numberStats(path: String) = {
    val numbers = Source.fromFile(path)
      .mkString.split("\\s+")
      .map(_.toDouble).toArray
    println(numbers.sum)
    println(numbers.sum / numbers.length)
    println(numbers.max)
    println(numbers.min)
  }

  // **5.Write a Scala program that writes the powers of 2 and their reciprocals to a file, with
  // the exponent ranging from 0 to 20. Line up the columns:**
  //      1     1
  //      2     0.5
  //      4     0.25
  //      ...   ...
  def powersAndReciprocals(path: String, base: Int, exponents: Range): Unit = {
    new PrintWriter(path) {
      for(exp <- exponents) {
        val a = math.pow(base,exp)
        write(f"${a.toLong.toString.padTo(10,' ')}${(1/a).toString}\n")
      }
      close()
    }
  }

  // **6.Make a regular expression searching for quoted strings "like this, maybe with \" or \\"
  // in a Java or C++ program. Write a Scala program that prints out all such strings in a source
  // file.**
  def quotedStrings(path: String): Unit = {
    val pattern = """"[^"]*"""".r
    val content = Source.fromFile(path)
      .mkString
    pattern.findAllIn(content)
      .foreach(println)
  }

  // **7.Write a Scala program that reads a text file and prints all tokens in the file that are
  // not floating-point numbers. Use a regular expression.**
  def nonFloatingPoint(path: String): Unit = {
    Source.fromFile(path)
      .mkString.split("\\s+")
      .filterNot(_.matches("[+-]?[0-9]*[.][0-9]+"))
      .foreach(println)
  }

  // **8.Write a Scala program that prints the src attributes of all img tags of a web page. Use
  // regular expressions and groups.**
  def srcImgTags(url: String): Unit = {
    val pattern = """(<img[^>]+)(src=")([^"]*)""".r
    val content = Source.fromURL(url, "UTF-8").mkString
    pattern.findAllIn(content)
      .foreach { token =>
        val pattern(_, _, path) = token
        println(path)
      }
  }

  // **9.Write a Scala program that counts how many files with .class extension are in a given
  // directory and its subdirectories.**
  def recursiveClassFileCount(path: String) = {
    import java.nio.file._
    val entries = Files.walk(Paths.get(path))
    try {
      entries.filter(_.getFileName.toString.endsWith(".class")).count
    } finally {
      entries.close()
    }
  }

  // **10.Expand the example in Section 9.8, “Serialization,” on page 113. Construct a few Person
  // objects, make some of them friends of others, and save an Array[Person] to a file. Read the
  // array back in and verify that the friend relations are intact.**
  class Person(val name: String) extends Serializable {
    private val friends = new ArrayBuffer[Person]

    def addFriend(friend: Person) = {
      friends += friend
    }

    def getFriends = {
      friends.toArray
    }

    final override def equals(other: Any) = {
      val that = other.asInstanceOf[Person]
      if (that == null) false
      else name == that.name
    }
  }

  def saveToFile(array: Array[Person], path: String) = {
    import java.io._
    val out = new ObjectOutputStream(new FileOutputStream(path))
    out.writeObject(array)
    out.close()
  }
}
