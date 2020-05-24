package impatient_exercises

import scala.math._

object Chapter2 {

  def signum(x: Int) = if(x > 0) 1 else if(x < 0) -1 else 0

  def countDown(n: Int) = {
    for(x <- (0 to n).reverse) println(x)
  }

  def unicodeProduct(str: String) = {
    var product = 1
    for(x: Char <- "Hello") {
      product *= x
    }
    product
  }

  def unicodeProduct2(str: String) = {
    str.codePoints.toArray.foldLeft(1)(_ * _)
  }

  def product(str: String) = {
    def product(result: Int, str: List[Char]): Int = str match {
      case Nil => result
      case x :: tail => product(result * x, tail)
    }
    product(1, str.toList)
  }

  def power(x: Double, n: Int): Double = n match {
    case n if n == 0 => 1
    case n if n == 1 => x
    case n if n < -1 => 1/x * power(1/x,abs(n) -1)
    case n if n % 2 != 0 => x * power(x, n-1)
    case n if n % 2 == 0 => power(x, n/2) * power(x, n/2)
  }


}
