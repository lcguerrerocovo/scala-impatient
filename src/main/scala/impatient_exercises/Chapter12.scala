// Example: Chapter 12: Higher-Order Functions

package impatient_exercises

import scala.collection.{GenSeq, GenSeqLike, IterableLike, Parallelizable}
import scala.collection.mutable.ListBuffer
import scala.collection.parallel.ParSeq

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
  def adjustToPair(op: (Int,Int) => Int)
  = (tuple : (Int,Int)) => op(tuple._1,tuple._2)

  // **8.In Section 12.8, “Currying,” on page 164, you saw the corresponds method used with two
  //     arrays of strings. Make a call to corresponds that checks whether the elements in an array
  //     of strings have the lengths given in an array of integers.**
  def arrayElementsSizeIs(strs: Array[String], sizes: Array[Int]) = {
    strs.corresponds(sizes)(_.length.equals(_))
  }

  // **9.Implement corresponds without currying. Then try the call from the preceding exercise.
  //     What problem do you encounter?**
  /*
     `corresponds(Array("cookie","sugar","tea"),Array(6,5,3),_.length.equals(_))`
     doesn't work because types can't be inferred with error:
       missing parameter type for expanded function
       ((x$1: <error>, x$2: <error>) => x$1.length.equals(x$2))

     `corresponds(Array("cookie","sugar","tea"),Array(6,5,3),(a: String,b: Int) => a.length.equals(b)`
     works because types are provided in the function literal
   */
  def corresponds[A,B](arr1: Seq[A], arr2: Seq[B], p: (A,B) => Boolean): Boolean = {
    val i = arr1.iterator
    val j = arr2.iterator
    while (i.hasNext && j.hasNext)
      if (!p(i.next(), j.next()))
        return false

    !i.hasNext && !j.hasNext
  }

  // **10.Implement an unless control abstraction that works just like if, but with an inverted
  //      condition. Does the first parameter need to be a call-by-name parameter? Do you need
  //      currying?
  /*
          - Using call-by-name in first parameter is not really needed because condition can be
            evaluated to a boolean on call, it does not need to be an expression that needs to be
            evaluated within the unless function
          - Currying is needed so that syntax is the same as if statement:
              unless(condition) { block }
   */
  def unless(condition: Boolean)(block: => Unit) = {
     if(!condition) block
  }
}
