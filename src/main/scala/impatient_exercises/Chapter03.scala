package impatient_exercises

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import scala.jdk.CollectionConverters._

object Chapter03 {

  def randomArray(n: Int): Array[Int] = {
    val array = ArrayBuffer[Int]()
    for(x <- 0 until n) {
      array += Random.nextInt
    }
    array.toArray
  }

  def swapAdjacent(arr: Array[Int]): Array[Int] = {
    val result = ArrayBuffer[Int]()
    for(x <- 0 until arr.length) {
      if(x % 2 == 0) result += arr(x)
      else result.insert(x-1,arr(x))
    }
    result.toArray
  }

  def swapAdjacentYield(arr: Array[Int]): Array[Int] = {
    val seq = for(x <- 0 until (arr.length,2)) yield {
      arr.drop(x).take(2).reverse
    }
    seq.reduce((x,y) => x ++ y)
  }

  def positivesFirst(arr: Array[Int]): Array[Int] = {
    arr.filter(x => x > 0) ++ arr.filter(x => x <= 0)
  }

  def arrAvg(arr: Array[Double]): Double = {
    arr.sum/arr.length
  }

  def reverseSortArray(arr: Array[Int]): Array[Int] =
    arr.sortWith(_ > _)

  def reverseSortArrayBuffer(arr: ArrayBuffer[Int]): ArrayBuffer[Int] =
    arr.sortWith(_ > _)

  def fropFirstNegative(arr: Array[Int]): Array[Int] = {
    val index = arr.indexWhere(x => x < 0)
    arr.take(index) ++ arr.drop(index+1)
  }

  def sortedAmericanTimeZones: Array[String] = {
    val tz = java.util.TimeZone.getAvailableIDs
    tz.filter((x: String) => x.startsWith("America/"))
      .map((x: String) => x.stripPrefix("America/"))
      .sorted
  }


}
