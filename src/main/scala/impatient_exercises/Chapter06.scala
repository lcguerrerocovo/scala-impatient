// Example: Chapter 6: Objects

package impatient_exercises

object Chapter06 {

  // **1.Write an object Conversions with methods inchesToCentimeters, gallonsToLiters, and
  // milesToKilometers.**
  object Conversions {
    val InchesToCentimeters = 2.54d
    val GallonsToLiters = 3.785d
    val MilesToKilometers = 1.609344d

    def inchesToCentimeters(inches: Double): Double = {
      inches * InchesToCentimeters
    }

    def gallonsToLiters(gallons: Double): Double = {
      gallons * GallonsToLiters
    }

    def milesToKilometers(miles: Double): Double = {
      miles * MilesToKilometers
    }
  }

  // **2.The preceding problem wasn’t very object-oriented. Provide a general super- class
  // UnitConversion and define objects InchesToCentimeters, GallonsToLiters, and MilesToKilometers
  // that extend it.**
  abstract class UnitConversion(val conversionFactor: Double)

  object InchesToCentimeters extends UnitConversion(2.54d) {
    def apply(inches: Double): Double = inches * conversionFactor
  }

  object GallonsToLiters extends UnitConversion(3.785d) {
    def apply(gallons: Double): Double = gallons * conversionFactor
  }

  object MilesToKilometers extends UnitConversion(1.609344d) {
    def apply(miles: Double): Double = miles * conversionFactor
  }

  // **3.Define an Origin object that extends java.awt.Point. Why is this not actually a good idea?
  // (Have a close look at the methods of the Point class.)**
  //
  //   - Because origin is static (0,0) by definition and should not be able to be changed, java.awt
  //   Point provides methods to change x and y coordinates and that would be undesired behavior
  //   for the Origin object

  // **4.Define a Point class with a companion object so that you can construct Point instances
  // as Point(3, 4), without using new.**
  object Point {
    def apply(x: Double, y: Double): Point = new Point(x,y)
  }

  class Point(val x: Double, val y: Double)

  // **5.Write a Scala application, using the App trait, that prints its command-line arguments in
  // reverse order, separated by spaces. For example, scala Reverse Hello World should print World
  // Hello.**
  class Reverse extends App {
    print(args.reverse.mkString(" "))
  }

  // **6.Write an enumeration describing the four playing card suits so that the toString method
  // returns proper suit.**
  //
  // **7.Implement a function that checks whether a card suit value from the preceding exercise
  // is red.**
  object PlayingCards extends Enumeration {
    type PlayingCards = Value

    val Hearts = Value("\u2665")
    val Clubs = Value("\u2663")
    val Diamonds = Value("\u2666")
    val Spades = Value("\u2660")

    def isRed(suit: PlayingCards.PlayingCards) = {
      suit == Hearts || suit == Diamonds
    }
  }

  // **8.Write an enumeration describing the eight corners of the RGB color cube. As IDs, use the
  // color values (for example, 0xff0000 for Red).**
  object ColorCube extends Enumeration {
    type ColorCube = Value

    val Black = Value(0x000000,"Black")
    val Blue = Value(0x0000ff,"Blue")
    val Red = Value(0xff0000,"Red")
    val Magenta = Value(0xff00ff,"Magenta")
    val Green = Value(0x00ff00,"Green")
    val Cyan = Value(0x00ffff,"Cyan")
    val Yellow = Value(0xffff00,"Yellow")
    val White = Value(0xffffff,"White")
  }
}
