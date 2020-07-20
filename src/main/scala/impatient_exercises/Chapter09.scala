package impatient_exercises

import java.io.PrintWriter

import scala.io.Source

object Chapter09 {

  /**
   * ===A class that provides a solution to Chapter 9 exercise 1  ===
   *
   * 1. Write a Scala code snippet that reverses the lines in a file (making the last line the
   *    first one, and so on).
   */
  def reverseFile(path: String): Unit  = {
    val reversedContents = Source.fromFile(path).getLines.toArray.reverse.mkString("\n")
    new PrintWriter(path) {
      write(reversedContents)
      close()
    }
  }

  /**
   * ===A class that provides a solution to Chapter 9 exercise 2  ===
   *
   * 2. Write a Scala program that reads a file with tabs, replaces each tab with spaces so that
   *    tab stops are at n-column boundaries, and writes the result to the same file.
   */
  def tabsToSpaces(path: String, spaces: Int): Unit  = {
    val contents = Source.fromFile(path)
      .mkString
      .replaceAll("\t", (" " * spaces))

    new PrintWriter(path) {
      write(contents)
      close()
    }
  }


}
