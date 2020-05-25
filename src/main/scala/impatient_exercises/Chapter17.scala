package impatient_exercises

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

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
    Await.result(fun(x),5.seconds)
  }
}
