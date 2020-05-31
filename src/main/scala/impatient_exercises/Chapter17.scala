package impatient_exercises

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.math.BigInt
import scala.jdk.CollectionConverters._

object Chapter17 {

  def flatMapFuture = {
    val future1 = Future { Thread.sleep(1000) ; 2 }
    val future2 = Future { Thread.sleep(1000) ; 40 }

    val result: Int = Await.result(future1.flatMap(x => future2.map(y => x + y)), 1.seconds)
    println(result)
  }

  def doInOrder[U,T,V](f: T => Future[U], g: U => Future[V])(t: T): Future[V] = {
    f(t).flatMap(x => g(x))
  }

  def doInOrder(x: Int): String = {
    def f: Int => Future[Double] = { x: Int =>
      Future[Double] { Thread.sleep(1000) ; x.toDouble/10 }
    }

    def g: Double => Future[String] = { y: Double =>
      Future[String] { Thread.sleep(1000) ; y.toString }
    }

    val fun = Chapter17.doInOrder(f,g) _
    Await.result(fun(x),3.seconds)
  }

  def doInSequence[T](funs: Seq[T => Future[T]])(t: T): Future[T] = funs match {
    case f :: Nil => f(t)
    case f :: tail => f(t).flatMap(x => doInSequence(tail)(x))
  }

  def doInSequence(x: Int, times: Int): Int = {
    def f: Int => Future[Int] = { x: Int =>
      Future[Int] { Thread.sleep(1000) ; x * 10 }
    }

    val fun: Int => Future[Int] = Chapter17.doInSequence(Seq.fill(times)(f)) _
    Await.result(fun(x),(1.seconds * times) + 1.seconds)
  }

  def doTogether[T,U,V](f: T => Future[U], g: T => Future[V])(t: T): Future[(U,V)] = {
    f(t) zip g(t)
  }

  def doTogether(x: Int): (Double,String) ={
    def f: Int => Future[Double] = { x: Int =>
      Future[Double] { Thread.sleep(1000) ; x.toDouble/10 }
    }

    def g: Int => Future[String] = { x: Int =>
      Future[String] { Thread.sleep(1000) ; x.toString }
    }

    val fun = Chapter17.doTogether(f,g) _
    Await.result(fun(x),2.seconds)
  }

  def eventuallyDoSequence[T](seq: Seq[Future[T]]) : Future[Seq[T]] = {
    Future.sequence(seq)
  }

  def eventuallyDoSequence: Seq[Int] = {
    def parts = Seq(1*1,2*2,3*4)
    def futures: Seq[Future[Int]] = parts.map(p => Future { p })
    Await.result(eventuallyDoSequence(futures), 1.seconds)
  }

  def repeat[T](action: => T, until: T => Boolean): Future[T] = {
    def eventuallyAction = Future { action }
    def eventuallyUntil(x: T) = Future { Thread.sleep(1000); until(x) }
    eventuallyAction.flatMap{ x =>
      eventuallyUntil(x).flatMap { y =>
        if (!y) repeat(action, until) else Future { x }
      }
    }
  }

  def countPrimes(n: Int): Future[Int] = {
    val partition = n / Runtime.getRuntime.availableProcessors
    Future.sequence {
      (1 to n)
        .map(x => (x, x / partition))
        .groupBy(_._2)
        .values
        .toList
        .map { x =>
          Future {
            x.count(y => BigInt(y._1).isProbablePrime(10))
          }
        }
    }.map(_.reduce(_ + _))
  }

  def getDoc(s: String): Future[Document] = Future {
    Jsoup.connect(s).get()
  }

  def getHeader(s: String, header: String = "Server"): Future[String] = Future {
    Jsoup
      .connect(s)
      .execute().header(header)
  }

  def readLinks(doc: Document): List[String] = {
    doc.select("a[href]")
      .asScala
      .map(el => el.attr("abs:href")).toList
  }

  def printLinks(doc: Document) = Future {
    readLinks(doc).mkString("")
  }

  def getLinkServerHeader(f: (String,String) => Future[String] = getHeader)(doc: Document)
  : Future[String] = {
    Future.traverse(readLinks(doc).filterNot(_.isEmpty).take(25)) { x: String =>
      f(x,"Server")
    }.map {
      _.groupBy(key => key).map {
        case (k, v) =>
          f"server: ${k}, count: ${v.size}"
      }.mkString("\n")
    }
  }

  def getLinks(f: String => Future[Document] = getDoc)
              (g: Document => Future[String] = printLinks) = {
    def readInput = Future { scala.io.StdIn.readLine() }
    readInput.flatMap ( url =>
      f(url).flatMap(
          doc =>g(doc)
      )
    )
  }
}
