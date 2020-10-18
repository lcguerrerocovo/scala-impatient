package impatient_exercises

import impatient_exercises.Chapter13._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

import scala.collection.mutable.ListBuffer

class Chapter13Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "indexes"

  it should "given a string, produces a map of sorted indexes of all characters" in {
    indexes("Mississippi")('M').toList should contain only  0
    indexes("Mississippi")('i').toList should contain inOrder (1, 4, 7, 10)
    indexes("Mississippi")('s').toList should contain inOrder (2,3,5,6)
    indexes("Mississippi")('p').toList should contain inOrder (8,9)
  }

  behavior of "indexes2"

  it should "given a string, produces a map of sorted indexes of all characters" in {
    indexes2("Mississippi")('M') should contain only  0
    indexes2("Mississippi")('i') should contain inOrder (1, 4, 7, 10)
    indexes2("Mississippi")('s') should contain inOrder (2,3,5,6)
    indexes2("Mississippi")('p') should contain inOrder (8,9)
  }

  behavior of "removeOdd"

  it should "remove every second element of a ListBuffer using remove()" in {
    removeOdd(ListBuffer('t','e','s','t','i','n','g')) shouldEqual ListBuffer('t','s','i','g')
  }

  behavior of "removeOdd2"

  it should "remove every second element of a ListBuffer appending to a new list" in {
    removeOdd2(ListBuffer('t','e','s','t','i','n','g')) shouldEqual ListBuffer('t','s','i','g')
  }

  it should "removeOdd2 be faster than removeOdd" in {
    utils.time { removeOdd(ListBuffer('t','e','s','t','i','n','g')) }("removeOdd") >
      utils.time {removeOdd2(ListBuffer('t','e','s','t','i','n','g')) }("removeOdd2")
  }

  behavior of "simple"

  it should "return a values in the map for the collection of keys" in {
    simple(Array("Tom", "Fred", "Harry"),Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)) shouldEqual
      Array(3, 5)
  }

  behavior of "mkString"

  it should "make a string composed of items in collection and separated by provided separator" in {
    mkString(Array(1,2,3),",") shouldEqual "1,2,3"
  }

  behavior of "zipMap"

  it should "it should zip and then map a function to zipped collection" in {
    val prices = List(5.0, 20.0, 9.95)
    val quantities = List(10, 2, 1)
    zipMap(prices, quantities, (x: Double, y: Int) => x * y) shouldEqual List(50.0, 40.0, 9.95)
  }

  behavior of "array2D"

  it should "take an array and slice it into a 2D array by half" in {
    array2D(Array(1, 2, 3, 4, 5, 6)) shouldEqual Array(Array(1, 2, 3), Array(4, 5, 6))
  }

  behavior of "mostTimezones"

  it should "return continent with most timezones from java.util.TimeZone.getAvailableIDs" in {
    mostTimeZones shouldEqual "America"
  }

  behavior of "letterFrequencies"

  it should "count letter frequencies in a string in parallel in a thread safe way" in {
    val str =
      """The mutable hashmap is not thread safe, reading a value in getOrElse does not block other
        |threads from reading that value at the same time, thus leading to incorrect counting of
        |frequencies""".stripMargin
    letterFrequency(str).toList.sortWith(_._2 > _._2).take(2) shouldEqual List(('e', 21),('t',16))
  }


}
