// Example: Chapter 8: Inheritance

package impatient_exercises

import java.awt.{Rectangle => JRectangle}
import java.nio.ByteBuffer

import scala.collection.mutable.ListBuffer


object Chapter08 {

  // 1.Extend the following BankAccount class to a CheckingAccount class that charges $1 for
  //   every deposit and withdrawal.
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

  // 2.Extend the BankAccount class of the preceding exercise into a class SavingsAccount that
  //   earns interest every month (when a method earnMonthlyInterest is called) and has three free
  //   deposits or withdrawals every month. Reset the transaction count in the
  //   earnMonthlyInterest method.
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

  // 4.Define an abstract class Item with methods price and description. A SimpleItem is an item
  //   whose price and description are specified in the constructor. Take advantage of the fact
  //   that a val can override a def. A Bundle is an item that contains other items. Its price is
  //   the sum of the prices in the bundle. Also provide a mechanism for adding items to the
  //   bundle and a suitable description method.
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

  // 5.Design a class Point whose x and y coordinate values can be provided in a constructor.
  //   Provide a subclass LabeledPoint whose constructor takes a label value and x and y
  //   coordinates, such as
  //   new LabeledPoint("Black Thursday", 1929, 230.07)

  class Point(val x: Double, val y: Double) {

    final override def equals(other: Any) = {
      val that = other.asInstanceOf[Point]
      if (that == null) false
      else x == x && y == y
    }
  }

  class LabeledPoint(override val x: Double, override val y: Double, val label: String)
    extends Point(x,y)

  // 6.Define an abstract class Shape with an abstract method centerPoint and subclasses Rectangle
  //   and Circle. Provide appropriate constructors for the subclasses and override the
  //   centerPoint method in each subclass.
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

  // 7.Provide a class Square that extends java.awt.Rectangle and has three constructors: one that
  //   constructs a square with a given corner point and width, one that constructs a square with
  //   corner (0, 0) and a given width, and one that constructs a square with corner (0, 0) and
  //   width 0.

  class Square(cornerPoint: Point, width: Int) extends JRectangle(cornerPoint.x.toInt,
    cornerPoint.y.toInt, width, width) {

    def this() {
      this(new Point(0,0),0)
    }

    def this(width: Int) {
      this(new Point(0,0),width)
    }
  }

}

// 8.Compile the Person and SecretAgent classes in Section 8.6, “Overriding Fields,” on page 95
//   and analyze the class files with javap. How many name fields are there? How many name
//   getter methods are there? What do they get? (Hint: Use the -c and -private options.)
class Person(val name: String) {
  override def toString = s"${getClass.getName}[name=$name]"
}

class SecretAgent(codename: String) extends Person(codename) {
  override val name = "secret" // Don’t want to reveal name . . .
  override val toString = "secret" // . . . or class name
}
/**
 * > javap -c -private impatient_exercises.Person
 *
 * Compiled from "Chapter08.scala"
 * public class impatient_exercises.Person {
 * private final java.lang.String name;
 *
 * public java.lang.String name();
 * Code:
 * 0: aload_0
 * 1: getfield      #13                 // Field name:Ljava/lang/String;
 * 4: areturn
 *
 * public java.lang.String toString();
 * Code:
 * 0: new           #18                 // class java/lang/StringBuilder
 * 3: dup
 * 4: ldc           #19                 // int 7
 * 6: invokespecial #23                 // Method java/lang/StringBuilder."<init>":(I)V
 * 9: aload_0
 * 10: invokevirtual #27                 // Method getClass:()Ljava/lang/Class;
 * 13: invokevirtual #32                 // Method java/lang/Class.getName:()Ljava/lang/String;
 * 16: invokevirtual #36                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
 * 19: ldc           #38                 // String [name=
 * 21: invokevirtual #36                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
 * 24: aload_0
 * 25: invokevirtual #40                 // Method name:()Ljava/lang/String;
 * 28: invokevirtual #36                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
 * 31: ldc           #42                 // String ]
 * 33: invokevirtual #36                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
 * 36: invokevirtual #44                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
 * 39: areturn
 *
 * public impatient_exercises.Person(java.lang.String);
 * Code:
 * 0: aload_0
 * 1: aload_1
 * 2: putfield      #13                 // Field name:Ljava/lang/String;
 * 5: aload_0
 * 6: invokespecial #48                 // Method java/lang/Object."<init>":()V
 * 9: return
 * }
 *
 * Compiled from "Chapter08.scala"
 * public class impatient_exercises.SecretAgent extends impatient_exercises.Person {
 * private final java.lang.String name;
 *
 * > javap -c -private impatient_exercises.SecretAgent
 *
 * Compiled from "Chapter08.scala"
 * public class impatient_exercises.SecretAgent extends impatient_exercises.Person {
 * private final java.lang.String name;
 *
 * private final java.lang.String toString;
 *
 * public java.lang.String name();
 * Code:
 * 0: aload_0
 * 1: getfield      #14                 // Field name:Ljava/lang/String;
 * 4: areturn
 *
 * public java.lang.String toString();
 * Code:
 * 0: aload_0
 * 1: getfield      #18                 // Field toString:Ljava/lang/String;
 * 4: areturn
 *
 * public impatient_exercises.SecretAgent(java.lang.String);
 * Code:
 * 0: aload_0
 * 1: aload_1
 * 2: invokespecial #23                 // Method impatient_exercises/Person."<init>":(Ljava/lang/String;)V
 * 5: aload_0
 * 6: ldc           #25                 // String secret
 * 8: putfield      #14                 // Field name:Ljava/lang/String;
 * 11: aload_0
 * 12: ldc           #25                 // String secret
 * 14: putfield      #18                 // Field toString:Ljava/lang/String;
 * 17: return
 * }
 */

// 9.In the Creature class of Section 8.10, “Construction Order and Early Definitions,” on page 98,
//   replace val range with a def.
//
//   What happens when you also use a def in the Ant subclass? Why?
//
//   - The env array gets initialized with the value set in the range def in the subclass
//
//   What happens when you use a val in the subclass? Why?
//
//   - When the env array is initialized, the value of range val in subclass has not yet been
//     initialized so it is set to 0
class Creature {
  def range: Int = 10
  val env: Array[Int] = new Array[Int](range)
}

class Ant extends Creature {
  override def range = 2
}

class Ant2 extends Creature {
  override val range = 2
}

// 10.The file scala/collection/immutable/Stack.scala contains the definition class Stack[A]
//    protected (protected val elems: List[A]). Explain the meanings of the protected keywords.
//    (Hint: Review the discussion of private constructors in Chapter 5.)
//
//    - The protected keyword makes the constructor accesible to the class and its subclasses, which
//    means that the primary constructor in this case is only accesible to the class and its
//    subclasses so users must use an auxiliary constructor to construct a Stack object

// 11.Define a value class Point that packs integer x and y coordinates into a Long (which you
//    should make private).
class Point(private val coordinates: Long) extends AnyVal {
  def x: Int = {
    val arr = (BigInt(coordinates).toByteArray.reverse padTo(8,0.toByte)).reverse
    ByteBuffer.wrap(arr.take(4)).getInt
  }
  def y: Int = {
    val arr = (BigInt(coordinates).toByteArray.reverse padTo(8,0.toByte)).reverse
    ByteBuffer.wrap(arr.drop(4)).getInt
  }
}

object Point {
  def apply(x: Int, y: Int) = {
    val packedArray = (BigInt(x).toByteArray.reverse padTo(4,0.toByte)).reverse ++
      (BigInt(y).toByteArray.reverse padTo(4,0.toByte)).reverse
    new Point(ByteBuffer.wrap(packedArray).getLong)
  }
}
