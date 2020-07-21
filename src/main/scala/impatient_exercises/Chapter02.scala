// Example: Chapter 2: Control structures

package impatient_exercises

import scala.math._

object Chapter02 {

  // **1.The signum of a number is 1 if the number is positive, –1 if it is negative, and 0 if it
  // is zero. Write a function that computes this value.**
  def signum(x: Int) = if(x > 0) 1 else if(x < 0) -1 else 0

  // **2.What is the value of an empty block expression {}? What is its type?**
  //
  // - Value is (), type is Unit

  // **3.Come up with one situation where the assignment x = y = 1 is valid in Scala.
  // (Hint: Pick a suitable type for x.)**
  //
  // - when x is of Unit type

  // **4.Write a Scala equivalent for the Java loop
  // `for (int i = 10; i >= 0; i--) System.out.println(i);`**
  def countDown(n: Int) = {
    for(x <- (0 to n).reverse) println(x)
  }

  // **6.Write a for loop for computing the product of the Unicode codes of all letters in a
  // string. For example, the product of the characters in "Hello" is 825152896.**
  def unicodeProduct(str: String) = {
    var product = 1
    for(x: Char <- "Hello") {
      product *= x
    }
    product
  }

  // **7.Solve the preceding exercise without writing a loop. (Hint: Look at the StringOps
  // Scaladoc.)**
  def unicodeProduct2(str: String) = {
    str.codePoints.toArray.foldLeft(1)(_ * _)
  }

  // **9.Make the function of the preceding exercise a recursive function.**
  def product(str: String) = {
    def product(result: Int, str: List[Char]): Int = str match {
      case Nil => result
      case x :: tail => product(result * x, tail)
    }
    product(1, str.toList)
  }

  // **10 Write a function that computes x , where n is an integer. Use the following
  // recursive definition: (refer to the book) don't use a return statement**
  def power(x: Double, n: Int): Double = n match {
    case n if n == 0 => 1
    case n if n == 1 => x
    case n if n < -1 => 1/x * power(1/x,abs(n) -1)
    case n if n % 2 != 0 => x * power(x, n-1)
    case n if n % 2 == 0 => power(x, n/2) * power(x, n/2)
  }


}
