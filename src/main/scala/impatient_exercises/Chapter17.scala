// Example: Chapter 17: Futures

package impatient_exercises

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.math.BigInt
import scala.collection.JavaConverters._

object Chapter17 {

  // **1.Consider the expression**
  for (
    n1 <- Future { Thread.sleep(1000) ; 2 };
    n2 <- Future { Thread.sleep(1000); 40 }
  ) println(n1 + n2)
  // **How is the expression translated to map and flatMap calls? Are the two futures executed
  // concurrently or one after the other? In which thread does the call to println occur?**
  //
  // `scala> Chapter17.flatMapFuture`
  def flatMapFuture = {
    val future1 = Future { Thread.sleep(1000) ; 2 }
    val future2 = Future { Thread.sleep(1000) ; 40 }

    val result: Int = Await.result(future1.flatMap(x => future2.map(y => x + y)), 1.seconds)
    println(result)
  }

  def doInOrder[U,T,V](f: T => Future[U], g: U => Future[V])(t: T): Future[V] = {
    f(t).flatMap(x => g(x))
  }

  // **2.Write a function doInOrder that, given two functions f: T => Future[U] and g: U => Future[V]
  // produces a function T => Future[U] that, for a given t, eventually yields g(f(t))**
  //
  // `scala> Chapter17.doInOrder(3)`
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

  // **3.Repeat the preceding exercise for any sequence of functions of type T => Future[T].""")**
  //
  // `scala> Chapter17.doInSequence(3,5))`
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

  // **4.Write a function doTogether that, given two functions f: T => Future[U] and g: U =>
  // Future[V], produces a function T => Future[(U, V)], running the two computations in parallel
  // and, for a given t, eventually yielding (f(t), g(t)).""")**
  //
  // `scala> println(Chapter17.doTogether(3))`
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

  // **5.Write a function that receives a sequence of futures and returns a future that
  // eventually yields a sequence of all results**
  //
  // `scala> Chapter17.eventuallyDoSequence`
  def eventuallyDoSequence: Seq[Int] = {
    def parts = Seq(1*1,2*2,3*4)
    def futures: Seq[Future[Int]] = parts.map(p => Future { p })
    Await.result(eventuallyDoSequence(futures), 1.seconds)
  }

  // **6.Write a method Future[T] repeat(action: => T, until: T => Boolean)
  // that asynchronously repeats the action until it produces a value that is accepted by the until
  // predicate, which should also run asynchronously. Test with a function that reads a password
  // from the console, and a function that simulates a validity check by sleeping for a second
  // and then checking that the password is "secret". Hint: Use recursion.**
  def repeat[T](action: => T, until: T => Boolean): Future[T] = {
    def eventuallyAction = Future { action }
    def eventuallyUntil(x: T) = Future { Thread.sleep(1000); until(x) }
    eventuallyAction.flatMap{ x =>
      eventuallyUntil(x).flatMap { y =>
        if (!y) repeat(action, until) else Future { x }
      }
    }
  }

  // **7.Write a program that counts the prime numbers between 1 and n, as reported by BigInt
  // .isProbablePrime.**
  //
  // **Divide the interval into p parts, where p is the number of available processors. Count the
  // primes in each part in concurrent futures and combine the results.")**
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

  def getLinks(doc: Document) = Future {
    readLinks(doc).mkString("\n")
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

  // **8.Write a program that asks the user for a URL, reads the web page at that URL, and
  // displays all the hyperlinks. Use a separate Future for each of these three steps.")**
  //
  // `scala> Await.result(Chapter17.getLinks(Chapter17.getDoc _)(),200.seconds)`

  // **9.Write a program that asks the user for a URL, reads the web page at that URL,
  // finds all the hyperlinks, visits each of them concurrently, and locates the Server HTTP
  // header for each of them. Finally, print a table of which servers were found how often. The
  // futures that visit each page should return the header.**
  //
  // `scala> Await.result(Chapter17.getLinks(Chapter17.getDoc _)(Chapter17.getLinkServerHeader
  //  ()),200.seconds)`
  def getLinks(f: String => Future[Document] = getDoc)
              (g: Document => Future[String] = getLinks): Future[String] = {
    def readInput = Future { scala.io.StdIn.readLine() }
    readInput.flatMap ( url =>
      f(url).flatMap(
          doc =>g(doc)
      )
    )
  }
}
