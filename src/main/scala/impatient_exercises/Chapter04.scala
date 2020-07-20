package impatient_exercises

import scala.collection.{immutable, mutable}
import java.util.Calendar._
import scala.collection.JavaConverters._

object Chapter04 {

 def priceChange(items: Map[String,Double], change: Double): Map[String,Double] = {
   for((k, v) <- items) yield( k -> v*change)
 }

  def javaMap: mutable.Map[String, Int] =
    new java.util.TreeMap[String, Int].asScala.withDefaultValue(0)
  def scalaMap: mutable.Map[String, Int] =
    mutable.HashMap[String,Int]().withDefaultValue(0)

  def readFileCountWordsMutable(filename: String)(map: mutable.Map[String,Int])
  : mutable.Map[String, Int] = {
    scala.io.Source.fromResource(filename)
      .getLines
      .flatMap(_.split(" "))
      .foreach(x =>
        map(x) = map(x) + 1
      )
    map
  }

  def unsortedMap = (lst: Seq[(String,Int)]) => immutable.Map(lst:_*)
  def sortedMap = (lst: Seq[(String,Int)]) => immutable.SortedMap(lst:_*)

  def readFileCountWordsImmutable(filename: String)(f: Seq[(String,Int)] => Map[String, Int])
  : Map[String, Int] = {
    val words = scala.io.Source.fromResource(filename)
      .getLines
      .flatMap(_.split(" ")).toList

    var wordCounts = f(words.distinct.map(k => k -> 0))
    words.foreach(x =>
      wordCounts = wordCounts + (x -> (wordCounts(x) + 1))
    )
    wordCounts
  }

  val linkedCalendarMap = mutable.LinkedHashMap(
      "Monday" -> MONDAY,
      "Tuesday" -> TUESDAY,
      "Wednesday" -> WEDNESDAY,
      "Thursday" -> THURSDAY,
      "Friday" -> FRIDAY,
      "Saturday" -> SATURDAY,
      "Sunday" -> SUNDAY
  )

  def printProperties: List[String] = {
    val props: mutable.Map[String, String] = System.getProperties.asScala
    val maxKey = props.keys.foldLeft(0)((x,y) => x max y.length)

    props.map { case (k,v) =>
      f"${k.padTo(maxKey+1,' ')}| $v"
    }.toList
  }

  def minMax(arr: Array[Int]): (Int,Int) = {
      arr.foldLeft((Int.MaxValue, Int.MinValue))(
        (x: (Int, Int), y: Int) => (x._1 min y, x._2 max y)
      )
  }

  def lteqgt(arr: Array[Int], v: Int): (Int, Int, Int) = {
    arr.foldLeft((0,0,0))(
      (x: (Int, Int, Int), y: Int) => x match {
        case (lessThan: Int, equalTo: Int, greaterThan: Int) =>
          if(y < v) (lessThan+1, equalTo, greaterThan)
          else if(y == v) (lessThan, equalTo+1, greaterThan)
          else (lessThan, equalTo, greaterThan+1)
      }
    )
  }

}
