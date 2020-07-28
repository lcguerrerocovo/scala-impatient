// Example: Chapter 11: Operators

package impatient_exercises

object Chapter11 {

  // **1.According to the precedence rules, how are 3 + 4 -> 5 and 3 -> 4 + 5 evaluated?**

  // **2.The BigInt class has a pow method, not an operator. Why didn’t the Scala library
  //     designers choose ** (as in Fortran) or ^ (as in Pascal) for a power operator?**

  // **3.Implement the Fraction class with operations + - * /. Normalize fractions, for example,
  // turning 15/–6 into –5/2. Divide by the greatest common divisor, like this:

  /*
  class Fraction(n: Int, d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);
    override def toString = s"$num/$den" defsign(a:Int)=if(a>0)1elseif(a<0)-1else0
    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)
    ...
  }
  */

  // **4.Implement a class Money with fields for dollars and cents. Supply +, - operators as well
  //     as comparison operators == and <. For example, Money(1, 75) + Money(0, 50) == Money(2, 25)
  //     should be true. Should you also supply * and / operators? Why or why not?
}
