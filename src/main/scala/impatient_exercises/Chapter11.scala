// Example: Chapter 11: Operators

package impatient_exercises

object Chapter11 {

  // **1.According to the precedence rules, how are 3 + 4 -> 5 and 3 -> 4 + 5 evaluated?**
  //
  //     - `-3 + 4 -> 5 = ((-3) + 4) -> 5 = (1, 5)`
  //     - `3 -> 4 + 5 = (3,4) + 4 = error`

  // **2.The BigInt class has a pow method, not an operator. Why didn’t the Scala library
  //     designers choose ** (as in Fortran) or ^ (as in Pascal) for a power operator?**
  //
  //     - Because of existing precedence rules. Precedence is decided by first character of the
  //     operator making `**` have the same precedence as multiplication and for `^` precedence
  //     would have had to be changed since it has lower precedence in the scala specification

  // **3.Implement the Fraction class with operations + - * /. Normalize fractions, for example,
  //     turning 15/–6 into –5/2. Divide by the greatest common divisor, like this:**

  /*
  class Fraction(n: Int, d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);
    override def toString = s"$num/$den"
    def sign(a:Int) = if(a>0) 1 else if (a<0) -1 else 0
    def gcd(a: Int, b: Int): Int = if (b == 0) abs(a) else gcd(b, a % b)
    ...
  }
  */
  class Fraction(n: Int, d: Int) {
    private val num: Int = if (d == 0) 1 else n * sign(d) / gcd(n, d);
    private val den: Int = if (d == 0) 0 else d * sign(d) / gcd(n, d);

    override def toString = s"$num/$den"
    def sign(a:Int)= if (a > 0) 1 else if (a < 0) -1 else 0
    def gcd(a: Int, b: Int): Int = if (b == 0) math.abs(a) else gcd(b, a % b)

    def +(other: Fraction) = {
      (this +- other)(_ + _)
    }

    def -(other: Fraction) = {
      (this +- other)(_ - _)
    }

    private def +-(other: Fraction)(op: (Int, Int) => Int) = {
      val pNum = op(num*other.den,other.num*den)
      val pDen = den*other.den
      val fractionGcd = gcd(pNum,pDen)
      new Fraction(pNum/fractionGcd,pDen/fractionGcd)
    }

    def /(other: Fraction) = {
      this * new Fraction(other.den,other.num)
    }

    def *(other: Fraction) = {
      val pNum = num*other.num
      val pDen = den*other.den
      val fractionGcd = gcd(pNum,pDen)
      new Fraction(pNum/fractionGcd,pDen/fractionGcd)
    }

    def toDouble: Double = num/den.toDouble

    final override def equals(other: Any) = {
      val that = other.asInstanceOf[Fraction]
      if (that == null) false
      else num == that.num && den == that.den
    }

  }

  // **4.Implement a class Money with fields for dollars and cents. Supply +, - operators as well
  //     as comparison operators == and <. For example, Money(1, 75) + Money(0, 50) == Money(2, 25)
  //     should be true. Should you also supply * and / operators? Why or why not?**
}
