package impatient_exercises

import java.io.{ByteArrayOutputStream, StringReader}

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.AsyncMockFactory

class Chapter17Test extends AsyncFlatSpec with AsyncMockFactory with Matchers {

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

  behavior of "get Links"

  it should "get all links from an url in separate futures" in {

    val doc = scala.io.Source.fromResource("index.html").mkString("")
    val stub = stubFunction[String, Document]
    stub.when("http://wikipedia.com")
      .returns(Jsoup.parse(doc))
    val inputStr =
      """http://wikipedia.com""".stripMargin
    val in = new StringReader(inputStr)
    val out = new ByteArrayOutputStream()
    Console.withOut(out) {
      Console.withIn(in) {
        Chapter17.getLinks(stub) map { _ =>
          assert(out.toString.filterNot(_.isWhitespace) == "http://www.yahoo.com/")
        }
      }
    }
  }

}
