// Example: Chapter 10: Traits

package impatient_exercise

import impatient_exercises.{Point => _, _}
import java.awt.Point
import java.awt.Rectangle
import java.beans.{PropertyChangeEvent, PropertyChangeListener, PropertyChangeSupport}
import java.io
import java.io.InputStream

import impatient_exercise.Chapter10.BufferedInputStream

object Chapter10 {

  // **1.The java.awt.Rectangle class has useful methods translate and grow that are unfortunately
  //     absent from classes such as java.awt.geom.Ellipse2D. In Scala, you can fix this problem.
  //     Define a trait RectangleLike with concrete methods translate and grow. Provide any
  //     abstract methods that you need for the implementation, so that you can mix in the trait
  //     like this:**
  //
  //     `val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
  //     egg.translate(10, -10)
  //     egg.grow(10, 20)`
  trait RectangleLike {
    def getX: Double
    def getY: Double
    def getWidth: Double
    def getHeight: Double
    def setFrame(x: Double, y: Double, w: Double, h: Double)

    def translate(dx: Int, dy: Int): Unit = {
      val rectangle: Rectangle = new Rectangle()
      rectangle.setRect(getX,getY,getWidth,getHeight)
      rectangle.translate(dx,dy)
      setFrame(rectangle.x,rectangle.y,rectangle.width,rectangle.height)
    }

    def grow(h: Int, v: Int) = {
      val rectangle: Rectangle = new Rectangle()
      rectangle.setRect(getX,getY,getWidth,getHeight)
      rectangle.grow(h,v)
      setFrame(rectangle.x,rectangle.y,rectangle.width,rectangle.height)
    }
  }

  // **2.Define a class OrderedPoint by mixing scala.math.Ordered[Point] into java.awt.Point.
  //     Use lexicographic ordering, i.e. (x, y) < (x’, y’) if x < x’ or x = x’ and y < y’**
  class OrderedPoint(x: Int, y: Int) extends Point(x, y) with
  Ordered[Point] {
    def compare(that: Point) = {
      if(this.getX < that.getX || (this.getX == that.getX && this.getY < that.getY)) -1
      else if(this.getX == that.getX && this.getY == that.getY) 0
      else 1
    }
  }

  // **4.Provide a CryptoLogger trait that encrypts the log messages with the Caesar cipher.
  //     The key should be 3 by default, but it should be overridable by the user. Provide usage
  //     examples with the default key and a key of –3.**
  // TODO check if negative number is working properly for caesar cypher shift
  trait CryptoLogger {
    val key = 3
    val intToChar = ((0 to 25) zip ('a' to 'z')).toMap
    val charToInt = (('a' to 'z') zip (0 to 25)).toMap.withDefaultValue(-1)

    def log(msg: String) = println(encrypt(msg))

    def encrypt(clearText: String): String = {
      clearText.map {
        case e if e.isLetter && charToInt(e.toLower) == -1 =>
          throw new Exception("unsupported character")
        case e if e.isLetter && e.isUpper => operation(e.toLower,key).toUpper
        case e if e.isLetter => operation(e,key)
        case _ =>
      }.mkString
    }

    def operation(char: Char, key: Int) = {
      if(key.signum == -1) intToChar(((charToInt(char) % 26) - (key % 26)) % 26)
      else intToChar(((charToInt(char) % 26) + (key % 26)) % 26)
    }
  }

  trait Logger {
    def log(msg: String)
  }

  abstract class BankAccount(initialBalance: Double) extends Logger {
    private var balance = initialBalance
    def currentBalance = log("currentBalance"); balance
    def deposit(amount: Double) = { balance += amount; log("depositing") ;balance }
    def withdraw(amount: Double) = { balance -= amount; log("withdrawing"); balance }
  }

  // **5.The JavaBeans specification has the notion of a property change listener, a standardized
  //     way for beans to communicate changes in their properties. The PropertyChangeSupport class
  //     is provided as a convenience superclass for any bean that wishes to support property
  //     change listeners. Unfortunately, a class that already has another superclass—such as
  //     JComponent—must reimplement the methods. Reimplement PropertyChangeSupport as a trait,
  //     and mix it into the java.awt.Point class.**
  trait ScalaPropertyChangeSupport {
    val pcs: PropertyChangeSupport

    def addPropertyChangeListener(listener: PropertyChangeListener) {
      this.pcs.addPropertyChangeListener(listener);
    }

    def removePropertyChangeListener(listener: PropertyChangeListener) {
      this.pcs.removePropertyChangeListener(listener);
    }

    def firePropertyChange(propertyName: String, oldValue: Any, newValue: Any): Unit = {
      this.pcs.firePropertyChange(propertyName, oldValue, newValue)
    }
  }

  class NotifyChangesPoint(private[this] val px: Int, private[this] val py: Int) extends java.awt.Point(px,py)
    with ScalaPropertyChangeSupport {
    override val pcs = new PropertyChangeSupport(this);

    override def setLocation(x: Double, y: Double) {
      val (oldX, oldY) = (this.x,this.y)
      super.setLocation(x,y)
      firePropertyChange("x", oldX, this.x)
      firePropertyChange("y", oldY, this.y)
    }

    override def move(x: Int, y: Int) {
      val (oldX, oldY) = (this.x,this.y)
      super.move(x,y)
      firePropertyChange("x", oldX, this.x)
      firePropertyChange("y", oldY, this.y)
    }

    override def translate(x: Int, y: Int) {
      val (oldX, oldY) = (this.x,this.y)
      super.translate(x,y)
      firePropertyChange("x", oldX, this.x)
      firePropertyChange("y", oldY, this.y)
    }
  }

  // **6.In the Java AWT library, we have a class Container, a subclass of Component that
  //     collects multiple components. For example, a Button is a Component, but a Panel is a
  //     Container. That’s the composite pattern at work. Swing has JComponent and JButton, but if
  //     you look closely, you will notice something strange. JComponent extends Container, even
  //     though it makes no sense to add other components to, say, a JButton. Ideally, the Swing
  //     designers would have preferred the design in Figure 10–4.
  //
  //     But that’s not possible in Java. Explain why not. How could the design be executed in
  //     Scala with traits?**
  //
  //     - A hypothetical `JContainer` couldn't inherit from two classes (`Container` and
  //       `JComponent`) and thus the design is not feasible in Java. In Scala we could create a
  //       trait `Container` that encapsulates the state and functionality to collect multiple
  //       components in the object and make JContainer extend JComponent and implement the
  //       `Container` trait like so => `JContainer extends JComponent with Container`

  // **7.Construct an example where a class needs to be recompiled when one of the mixins changes.
  //     Start with class SavingsAccount extends Account with ConsoleLogger. Put each class and
  //     trait in a separate source file. Add a field to Account. In Main (also in a separate
  //     source file), construct a SavingsAccount and access the new field. Recompile all files
  //     except for SavingsAccount and verify that the program works. Now add a field to
  //     ConsoleLogger and access it in Main. Again, recompile all files except for SavingsAccount.
  //     What happens? Why?**

  // **8.There are dozens of Scala trait tutorials with silly examples of barking dogs or
  //     philosophizing frogs. Reading through contrived hierarchies can be tedious and not very
  //     helpful, but designing your own is very illuminating. Make your own silly trait hierarchy
  //     example that demonstrates layered traits, concrete and abstract methods, and concrete and
  //     abstract fields.**

  // **9.In the java.io library, you add buffering to an input stream with a BufferedInputStream
  //     decorator. Reimplement buffering as a trait. For simplicity, override the read method.**
  //  [decorator pattern in java.io](https://kymr.github
  //  .io/2016/11/27/Decorator-Pattern/#:~:text=BufferedInputStream%20is%20Concrete%20Decorator%20class.&text=FileInputStream%20is%20InputStream%20that%20can,from%20the%20stream%20to%20buffer.)
  trait BufferedInputStream extends InputStream {
    val bis: java.io.BufferedInputStream

    override def read(): Int = {
      this.bis.read()
    }

    def readChar(): Char = {
      this.read.asInstanceOf[Char]
    }
  }

  // **10.Using the logger traits from this chapter, add logging to the solution of the preceding
  //      problem that demonstrates buffering.**

  // **11.Implement a class IterableInputStream that extends java.io.InputStream with the trait
  //      Iterable[Byte].**

  // **12.Using javap -c -private, analyze how the call super.log(msg) is translated to Java. How
  //      does the same call invoke two different methods, depending on the mixin order?**
}
