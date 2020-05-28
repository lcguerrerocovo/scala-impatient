package impatient_exercises

import java.io.StringReader
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers

class Chapter17Test extends AsyncFlatSpec with Matchers {

  behavior of "repeat async"

  it should "repeat until password is passed from stdin" in {

    val inputStr =
      """|testing
         |input
         |from
         |stdin
         |secret
      """.stripMargin
    val in = new StringReader(inputStr)
    Console.withIn(in) {
      val futureRes = Chapter17.repeat[String](scala.io.StdIn.readLine(), _ == "secret")
      futureRes map { res =>
          assert(res == "secret")
        }
      }
    }

  behavior of "number of primes"

  it should "compute number of primes between 1 and n" in {
    Chapter17.countPrimes(100000) map { x =>
      assert(x == 9592)
    }
  }
}
