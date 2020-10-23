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
}
