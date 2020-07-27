package impatient_exercises

import java.io.ByteArrayOutputStream

import impatient_exercise.Chapter10.{BankAccount, CryptoLogger, NotifyChangesPoint, OrderedPoint, RectangleLike}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class Chapter10Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "RectangleLike"

  it should "provide concrete implementations for translate" in  {
    val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
    egg.translate(10,-10)

    egg.x shouldEqual 15.0
    egg.y shouldEqual 0.0
    egg.width shouldEqual 20.0
    egg.height shouldEqual 30.0
  }

  it should "provide concrete implementations for grow" in  {
    val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
    egg.grow(10,20)

    egg.x shouldEqual -5.0
    egg.y shouldEqual -10.0
    egg.width shouldEqual 40.0
    egg.height shouldEqual 70.0
  }

  behavior of "OrderedPoint"

  it should "compare two points using lexicographical ordering, left hand side being lesser" in {
    new OrderedPoint(0,2) < new OrderedPoint(1,2) shouldEqual true
    new OrderedPoint(1,2) < new OrderedPoint(1,3) shouldEqual true
  }

  it should "compare two points using lexicographical ordering being both equal" in {
    new OrderedPoint(1,2) == new OrderedPoint(1,2) shouldEqual true
  }

  it should "compare two points using lexicographical ordering, right hand side being lesser" in {
    new OrderedPoint(1,3) > new OrderedPoint(1,2) shouldEqual true
    new OrderedPoint(1,2) > new OrderedPoint(0,2) shouldEqual true
  }

  behavior of "CryptoLogger"

  it should "encrypt log messages using caesar cypher with default key" in {
    val out = new ByteArrayOutputStream
    Console.withOut(out) {
      val account = new BankAccount(10) with CryptoLogger
      account.currentBalance
      account.deposit(10)
      account.withdraw(5)
      out.toString shouldEqual """fxuuhqwEdodqfh
                                 |ghsrvlwlqj
                                 |zlwkgudzlqj
                                 |""".stripMargin
    }
  }

  it should "encrypt log messages using caesar cypher with non default key" in {
    val out = new ByteArrayOutputStream
    Console.withOut(out) {
      val account = new {
        override val key = -3
      } with BankAccount(10) with CryptoLogger
      account.currentBalance
      account.deposit(10)
      account.withdraw(5)
      out.toString shouldEqual """fxuuhqwEdodqfh
                                 |ghsrvlwlqj
                                 |zlwkgudzlqj
                                 |""".stripMargin
    }
  }

  behavior of "NotifyChangesPoint"

  it should "notify listener whenever changes are made to the class's fields" in {
    val point = new NotifyChangesPoint(1,2)
    point.x =  2
    point.y =  3
    point.x shouldEqual 2
    point.y shouldEqual 3
  }


}
