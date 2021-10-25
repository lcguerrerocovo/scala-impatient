package impatient_exercises

import impatient_exercises.Chapter08.{Bundle, CheckingAccount, Circle, Item, LabeledPoint, Line, Point, Rectangle, SavingsAccount, SimpleItem, Square}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalacheck.Gen
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class Chapter08Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  val Tolerance = 0.001d

  behavior of "CheckingAccount"

  it should " charge a dollar for deposits and withdrawals" in {
    val account = new CheckingAccount(10)
    account.deposit(2) shouldEqual 11
    account.withdraw(2) shouldEqual 8
  }

  behavior of "SavingsAccount"

  it should "accrue monthly interest" in {
    val account = new SavingsAccount(10)
    account.earnMonthlyInterest()
    account.currentBalance shouldEqual 10.008 +- Tolerance
    account.earnMonthlyInterest()
    account.earnMonthlyInterest()
    account.earnMonthlyInterest()
    account.currentBalance shouldEqual 10.033 +- Tolerance
    account.earnMonthlyInterest()
    account.earnMonthlyInterest()
    account.earnMonthlyInterest()
    account.earnMonthlyInterest()
    account.earnMonthlyInterest()
    account.earnMonthlyInterest()
    account.earnMonthlyInterest()
    account.earnMonthlyInterest()
    account.currentBalance shouldEqual 10.1 +- Tolerance
  }

  it should "provide 3 free transactions in a month" in {
    val account = new SavingsAccount(10)
    account.deposit(1) shouldEqual 11
    account.deposit(1) shouldEqual 12
    account.withdraw(2) shouldEqual 10
  }

  it should "charge transactions after the first 3" in {
    val account = new SavingsAccount(10)
    account.deposit(1) shouldEqual 11
    account.deposit(1) shouldEqual 12
    account.withdraw(2) shouldEqual 10
    account.deposit(2) shouldEqual 11
    account.withdraw(2) shouldEqual 8
  }

  it should "reset provide more free transactions after month has passed" in {
    val account = new SavingsAccount(10)
    account.deposit(1) shouldEqual 11
    account.deposit(1) shouldEqual 12
    account.withdraw(2) shouldEqual 10
    account.deposit(1) shouldEqual 10
    account.earnMonthlyInterest()
    account.deposit(1) shouldEqual 11.008 +- Tolerance
    account.deposit(1) shouldEqual 12.008 +- Tolerance
    account.withdraw(2) shouldEqual 10.008 +- Tolerance
  }

  behavior of "SimpleItem"

  it should "create an SimpleItem through constructor and have it be a subclass of Item" in {
    val item = new SimpleItem(4.05, "chewing gum")
    item.isInstanceOf[Item] shouldEqual true
    item.isInstanceOf[SimpleItem] shouldEqual true
    item.getClass shouldEqual classOf[SimpleItem]
    item.price shouldEqual 4.05
    item.description shouldEqual "chewing gum"
  }

  behavior of "Bundle"

  it should "be a class of items that contains other items" in {
    val bundle = new Bundle
    bundle.addItem(new SimpleItem(3, "chewing gum"))
    bundle.addItem(new SimpleItem(2, "tobacco"))
    bundle.addItem(new SimpleItem(5, "spoon"))

    val innerBundle = new Bundle
    innerBundle.addItem(new SimpleItem(5, "fork"))
    innerBundle.addItem(new SimpleItem(5, "knife"))
    innerBundle.addItem(new SimpleItem(20, "watch"))

    bundle.addItem(innerBundle)

    bundle.isInstanceOf[Item] shouldEqual true
    bundle.isInstanceOf[Bundle] shouldEqual true
    bundle.getClass shouldEqual classOf[Bundle]

    bundle.price shouldEqual 40
    bundle.description shouldEqual "chewing gum:tobacco:spoon:fork:knife:watch"
  }

  behavior of "LabeledPoint"

  it should "provide a Point with x y coordinates and a label" in {
    val labeledPoint = new LabeledPoint(1929, 230.07, "Black Thursday")
    labeledPoint.isInstanceOf[LabeledPoint] shouldEqual true
    labeledPoint.isInstanceOf[Chapter08.Point] shouldEqual true
    labeledPoint.getClass shouldEqual classOf[LabeledPoint]

    labeledPoint.x shouldEqual 1929
    labeledPoint.y shouldEqual 230.07
    labeledPoint.label shouldEqual "Black Thursday"
  }

  behavior of "Rectangle"

  it should "compute centerpoint" in {
    val left = new Line(new Chapter08.Point(1,4), new Chapter08.Point(3,2))
    val right = new Line(new Chapter08.Point(4,6), new Chapter08.Point(6,4))
    val rectangle = new Rectangle(left, right)

    rectangle.centerpoint shouldEqual new Chapter08.Point(3.5,4)
  }

  behavior of "Circle"

  it should "compute centerpoint" in {
    val circle = new Circle(new Chapter08.Point(0,0), 2)
    circle.centerpoint shouldEqual new Chapter08.Point(0,0)
  }

  behavior of "Square"

  it should "create a Square providing a point and a width" in {
    val square = new Square(new Chapter08.Point(1,1), 2)
    square.x shouldEqual 1
    square.y shouldEqual 1
    square.width shouldEqual 2
    square.height shouldEqual 2
  }

  it should "create a Square with default corner providing a width" in {
    val square = new Square(3)
    square.x shouldEqual 0
    square.y shouldEqual 0
    square.width shouldEqual 3
    square.height shouldEqual 3
  }

  it should "create a default Square" in {
    val square = new Square()
    square.x shouldEqual 0
    square.y shouldEqual 0
    square.width shouldEqual 0
    square.height shouldEqual 0
  }

  behavior of "Ant"

  it should "override super class range def initializing env array to proper size" in {
    val ant = new Ant
    ant.env.length shouldEqual 2
  }

  behavior of "Ant2"

  it should "at initialization of env array, range val is uninitialized, thus range should be 0 " +
    "at construction" in {
    val ant = new Ant2
    ant.env.length shouldEqual 0
  }

  behavior of "Point"

  it should "value class Point should pack x and y int coordinates in a long" in {
    val anyInt = Gen.choose(Int.MinValue,Int.MaxValue)
    forAll (anyInt,anyInt) {
      (x: Int, y: Int) =>
        val point = Point(x, y)
        point.x shouldEqual x
        point.y shouldEqual y
    }
  }
}
