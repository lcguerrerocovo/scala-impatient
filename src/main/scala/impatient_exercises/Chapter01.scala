package impatient_exercises

import scala.math._
import scala.util.Random



object Chapter01 {

  def floatingPointDiff = 3 - pow(sqrt(3),2)

  def multiplyString = "crazy" * 3

  def maxOfInts = 10 max 2

  def bigIntPower = BigInt(2).pow(1024)

  def randomeFileName = BigInt(128,Random).toString(36)

  def firstAndLast = {
    val str = "testString"
    f"string:${str} first:${str.head} last:${str.last}"
  }

}
