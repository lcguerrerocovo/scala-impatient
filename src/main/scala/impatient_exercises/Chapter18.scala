// Example: Chapter 18: Type Parameters

package impatient_exercises

object Chapter18 {

  // **1.Define an immutable class Pair[T, S] with a method swap that returns a new pair with the
  //     components swapped.**
  case class Pair[T, S](first: T, second: S) {
    def swap: Pair[S, T] = new Pair[S, T](second, first)
  }

}
