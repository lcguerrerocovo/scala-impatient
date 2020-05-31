package impatient_exercises

import java.io.{ByteArrayOutputStream, StringReader}

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.scalatest.flatspec.AsyncFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest.ParallelTestExecution

import scala.concurrent.Future

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

  behavior of "get Links"

  it should "get all links from an url in separate futures" in {

    val doc = Jsoup.parse(scala.io.Source.fromResource("index.html").mkString(""))
    def getDoc(s:String) = Future.successful(doc)
    val inputStr =
      """http://wikipedia.com""".stripMargin
    val in = new StringReader(inputStr)
    Console.withIn(in) {
      Chapter17.getLinks(getDoc)() map { e =>
        assert(e == "http://www.yahoo.com/http://www.yahoo.com/")
      }
    }
  }

  behavior of "get server headers"

  it should "get all server headers from links in an url and print table" in {

    val doc = Jsoup.parse(scala.io.Source.fromResource("index.html").mkString(""))
    def getDoc(s:String) = Future.successful(doc)
    def getHeader(s: String, s2: String) = Future.successful("ATS/8.0.7")

    val inputStr = """http://wikipedia.com""".stripMargin
    val in = new StringReader(inputStr)
    val out = new ByteArrayOutputStream()
    Console.withIn(in) {
      Chapter17.getLinks(getDoc)(Chapter17.getLinkServerHeader(getHeader)) map { e =>
        assert(e == "server: ATS/8.0.7, count: 2")
      }
    }
  }

}
