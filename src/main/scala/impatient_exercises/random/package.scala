package impatient_exercises

/**
 *  custom random number generation
 */
package object random {
  import scala.math.pow

  var seed = 0
  val a = 1664525
  val b = 1013904223
  val n = 32

  def setSeed(seed: Int) = {
    this.seed = seed
  }

  def nextInt(): Int = {
    nextDouble().toInt
  }

  def nextDouble(): Double = {
    (seed * a + b) % pow(2,n)
  }
}
