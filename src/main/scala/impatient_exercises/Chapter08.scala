package impatient_exercises

import java.awt.Point

import scala.collection.mutable.ListBuffer


object Chapter08 {

  /**
   * ===A class that provides a solution to Chapter 8 exercise 1  ===
   *
   * 1. Extend the following BankAccount class to a CheckingAccount class that charges $1 for
   * every deposit and withdrawal.
   */
  class BankAccount(initialBalance: Double) {
    private var balance = initialBalance
    def currentBalance = balance
    def deposit(amount: Double) = { balance += amount; balance }
    def withdraw(amount: Double) = { balance -= amount; balance }
  }

  class CheckingAccount(initialBalance: Double) extends BankAccount(initialBalance) {
    override def deposit(amount: Double): Double = {
      super.deposit(amount - 1)
    }

    override def withdraw(amount: Double): Double = {
      super.withdraw(amount + 1)
    }
  }

  /**
   * ===A class that provides a solution to Chapter 8 exercise 2  ===
   *
   * 2. Extend the BankAccount class of the preceding exercise into a class SavingsAccount that
   *    earns interest every month (when a method earnMonthlyInterest is called) and has three free
   *    deposits or withdrawals every month. Reset the transaction count in the
   *    earnMonthlyInterest method.
   */

  class SavingsAccount(initialBalance: Double) extends BankAccount(initialBalance) {
    val FreeTransactions = 3
    var monthlyFreeTransactionCount = FreeTransactions
    val monthlyInterest = 0.01/12

    private def resetTransactionCount(): Integer = {
      monthlyFreeTransactionCount = FreeTransactions
      monthlyFreeTransactionCount
    }

    private def decreaseTransactionCount() = {
      monthlyFreeTransactionCount -= 1
    }

    def earnMonthlyInterest(): Unit = {
      super.deposit(currentBalance * monthlyInterest)
      resetTransactionCount
    }

    override def deposit(amount: Double): Double = {
      if(monthlyFreeTransactionCount > 0) {
        decreaseTransactionCount()
        super.deposit(amount)
      } else {
        super.deposit(amount - 1)
      }
    }

    override def withdraw(amount: Double): Double = {
      if(monthlyFreeTransactionCount > 0) {
        decreaseTransactionCount()
        super.withdraw(amount)
      } else {
        super.withdraw(amount + 1)
      }
    }
  }

  /**
   * ===A class that provides a solution to Chapter 8 exercise 4  ===
   *
   * 4. Define an abstract class Item with methods price and description. A SimpleItem is an item
   *    whose price and description are specified in the constructor. Take advantage of the fact
   *    that a val can override a def. A Bundle is an item that contains other items. Its price is
   *    the sum of the prices in the bundle. Also provide a mechanism for adding items to the
   *    bundle and a suitable description method.
   */

  abstract class Item {
    def price: Double
    def description: String
  }

  class SimpleItem(val price: Double, val description: String) extends Item

  class Bundle extends Item {
    var items: ListBuffer[Item] = ListBuffer[Item]()

    override def price: Double = {
      items.map(_.price)
        .reduce(_ + _)
    }

    override def description: String = {
      items.map(_.description) mkString ":"
    }

    def addItem(item: Item): List[Item] = {
      items += item
      items.toList
    }
  }

  /**
   * ===A class that provides a solution to Chapter 8 exercise 5  ===
   *
   * 5. Design a class Point whose x and y coordinate values can be provided in a constructor.
   *    Provide a subclass LabeledPoint whose constructor takes a label value and x and y
   *    coordinates, such as
   *
   *    new LabeledPoint("Black Thursday", 1929, 230.07)
   */

  class Point(val x: Double, val y: Double) {

    final override def equals(other: Any) = {
      val that = other.asInstanceOf[Point]
      if (that == null) false
      else x == x && y == y
    }
  }

  class LabeledPoint(override val x: Double, override val y: Double, val label: String)
    extends Point(x,y)

  /**
   * ===A class that provides a solution to Chapter 8 exercise 6  ===
   *
   * 6. Define an abstract class Shape with an abstract method centerPoint and subclasses Rectangle
   *    and Circle. Provide appropriate constructors for the subclasses and override the
   *    centerPoint method in each subclass.
   */

  abstract class Shape {
    def centerpoint: Point
  }

  class Line(val start: Point, val end: Point)

  class Rectangle(val leftSide: Line, val rightSide: Line) extends Shape {
    override def centerpoint: Point = {
      val points = ListBuffer[Point](
        leftSide.start,
        leftSide.end,
        rightSide.start,
        rightSide.end
      )

      val xMax = points.map(_.x).reduce(_ max _)
      val xMin = points.map(_.x).reduce(_ min _)
      val yMax = points.map(_.y).reduce(_ max _)
      val yMin = points.map(_.y).reduce(_ min _)

      new Point((xMax - xMin)/2, (yMax - yMin)/2)
    }
  }

  class Circle(val origin: Point, val radius: Double) extends Shape {
    override def centerpoint: Point = origin
  }

  /**
   * ===A class that provides a solution to Chapter 8 exercise 7  ===
   *
   * 7. Provide a class Square that extends java.awt.Rectangle and has three constructors: one that
   *    constructs a square with a given corner point and width, one that constructs a square with
   *    corner (0, 0) and a given width, and one that constructs a square with corner (0, 0) and
   *     width 0.
   */
}