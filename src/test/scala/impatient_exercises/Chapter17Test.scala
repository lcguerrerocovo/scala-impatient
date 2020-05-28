package impatient_exercises

import java.io.{ByteArrayInputStream, StringReader}
import java.nio.charset.StandardCharsets

import org.scalatest.flatspec.{AnyFlatSpec, AsyncFlatSpec}
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Await

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
}
