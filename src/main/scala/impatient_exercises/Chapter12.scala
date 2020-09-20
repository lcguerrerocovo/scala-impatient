// Example: Chapter 12: Higher-Order Functions

package impatient_exercises

import scala.collection.mutable.ListBuffer

object Chapter12 {

  // **1.Write a function values(fun: (Int) => Int, low: Int, high: Int) that yields a collection
  //     of function inputs and outputs in a given range.**
  //
  //     - For example, values(x => x*x,-5,5) should produce a collection of pairs (-5,25),(-4,
  //     16),(-3,9), ..., (5, 25).
  /**
   * values function does not check for integer overflow
   */
  def values(fun: Int => Int, low: Int, high: Int): Seq[(Int,Int)] = {
    for {
      l <- low to high
    } yield(l, fun(l))
  }

  // **2.How do you get the largest element of an array with reduceLeft?**
  def largestElement(arr: Array[Int]): Int = arr.reduceLeft(_ max _)

  // **3.Implement the factorial function using to and reduceLeft, without a loop or
  //     recursion.**
  /**
   * does not check if x < 0
   */
  def factorial(x: Int): Int = {
    if(x == 0) 1
    else (1 to x).reduceLeft(_ * _)
  }

  // **4.The previous implementation needed a special case when n < 1. Show how you can avoid this
  //     with foldLeft. (Look at the Scaladoc for foldLeft. It’s like reduceLeft, except that the
  //     first value in the chain of combined values is supplied in the call.)**
  /**
   * does not check if x < 0
   */
  def factorial2(x: Int): Int = {
    (1 to x).foldLeft(1)(_ * _)
  }

  // **5.Write a function largest(fun: (Int) => Int, inputs: Seq[Int]) that yields the largest value
  //     of a function within a given sequence of inputs. For example, largest(x => 10 * x - x * x,
  //     1 to 10) should return 25. Don’t use a loop or recursion.**
  def largest(fun: Int => Int, inputs: Seq[Int]): Int = {
    inputs.tail.foldLeft(fun(inputs.head))(_ max fun(_))
  }

  // **6.Modify the previous function to return the input at which the output is largest. For
  //     example, largestAt(x => 10 * x - x * x, 1 to 10) should return 5. Don’t use a loop or
  //     recursion.**
  def largestWithParameter(fun: Int => Int, inputs: Seq[Int]): Int = {
    inputs.reduceLeft((a,b) => if(fun(a) >= fun(b)) a else b)
  }

  // **7.It’s easy to get a sequence of pairs, for example:
  //       val pairs= (1 to 10) zip (11 to 20)
  //     Now, suppose you want to do something with such a sequence—say, add up the values. But
  //     you can’t do
  //       pairs.map(_ + _)
  //     The function _ + _ takes two Int parameters, not an (Int, Int) pair. Write a function
  //     adjustToPair that receives a function of type (Int, Int) => Int and returns the equivalent
  //     function that operates on a pair. For example, adjustToPair(_ * _)((6, 7)) is 42. Then
  //     use this function in conjunction with map to compute the sums of the elements in pairs.**
}