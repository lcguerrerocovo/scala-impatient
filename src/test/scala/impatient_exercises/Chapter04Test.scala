package impatient_exercises

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.collection.mutable

class Chapter04Test extends AnyFlatSpec with Matchers {

  behavior of "wordCount mutable and immutable maps"

  it should "be equal" in {
    val mutableMap = Chapter04.readFileCountWordsMutable("words.txt")(Chapter04.scalaMap)
    val immutableMap = Chapter04.readFileCountWordsImmutable("words.txt")(
        Chapter04.unsortedMap
      )

    for(k <- mutableMap.keys) {
      mutableMap(k) shouldEqual immutableMap(k)
    }
  }

  it should "sort words properly if using sorted Map" in {
    val immutableMap = Chapter04.readFileCountWordsImmutable("words.txt")(
      Chapter04.sortedMap
    )

    val words = scala.io.Source.fromResource("words.txt")
      .getLines
      .flatMap(_.split(" ")).toList.distinct.sorted

    immutableMap.keys.toList shouldEqual words
  }

  behavior of "LinkedHashMap"

  it should "visit keys in insertion order" in {
    Chapter04.linkedCalendarMap.keys equals
      List("Monday","Tuesday","Wednesday","Thursday", "Friday","Saturday","Sunday")
  }

  behavior of "minmax"

  it should "return a tuple with max and min values of array" in  {
    Chapter04.minMax(Array(34,-1234,3535,4,0,1)) shouldEqual (-1234,3535)
  }

  behavior of "lteqgt"

  it should "return a tuple containing the counts of values less than v, equal to v, and greater than v" in  {
    Chapter04.lteqgt(Array(34,-1234,3535,4,0,1),4) shouldEqual (3,1,2)
  }


}
