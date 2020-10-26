// Example: Chapter 18: Type Parameters

package impatient_exercises

object Chapter18 {

  // **1.Define an immutable class Pair[T, S] with a method swap that returns a new pair with the
  //     components swapped.**
  case class Pair[T, S](first: T, second: S) {
    def swap: Pair[S, T] = new Pair[S, T](second, first)
  }

  // **2.Define a mutable class Pair[T] with a method swap that swaps the components of the pair.**
  class Pair2[T](var first: T, var second: T) {
    def swap: Pair2[T] = {
      val tmp = first
      first = second
      second = tmp
      this
    }

    final override def equals(other: Any): Boolean = {
      val that = other.asInstanceOf[Pair2[T]]
      if (that == null) false
      else first == that.first && second == that.second
    }
  }

  object Pair2 {
    def apply[T](first: T, second: T) = new Pair2(first, second)
  }

  // **3.Given a class Pair[T, S], write a generic method swap that takes a pair as its
  //     argument and returns a new pair with the components swapped.**
  def swap[T,S](pair: Pair[T, S]): Pair[S, T] = {
    new Pair[S, T](pair.second, pair.first)
  }

  // **4.Why don’t we need a lower bound for the replaceFirst method in Section 18.3, “Bounds for
  //     Type Variables,” on page 266 if we want to replace the first component of a Pair[Person]
  //     with a Student?**
  //
  //     - A Student is also of type Person since Student is a subtype of Person, any other
  //       subtype of Person can then be passed to the `replaceFirst` method without determining
  //       a lower bound

  //  **5.Why does RichInt implement Comparable[Int] and not Comparable[RichInt]?
  //
  //    - RichInt is a wrapper class for Int, thus the interface we want to extend from is
  //      Comparable[Int] which is the type that is being wrapped. RichInt just provides
  //      additional functionality to the primitive scala Int type

  // **6.Write a generic method middle that returns the middle element from any
  //     Iterable[T]. For example, middle("World") is 'r'.
  def middle[T](iter: Iterable[T]): T = {
    iter.take(iter.size/2 + 1).last
  }

  // **7.Look through the methods of the Iterable[+A] trait. Which methods use the
  //     type parameter A? Why is it in a covariant position in these methods?
  //
  //     - example: def copyToArray[B >: A](xs: Array[B], start: Int, len: Int) from
  //     IterableLike[A+]
  //     - in this method there is a lower bound set where A needs to be a subtype of B.
  //       Supposing there was a third type Z, if B -> A -> Z, then Iterable[B] >:
  //       Iterable[Z], thus A needs to be set as covariant so that Z can only be subtypes
  //       of A implementing the required interfaces to uphold B >: A (B >: Z would also hold
  //       true). if Z was NOT a subtype then Iterable[B] >: Iterable[Z] would not hold because
  //       B is not necessarily a supertype of Z
}
