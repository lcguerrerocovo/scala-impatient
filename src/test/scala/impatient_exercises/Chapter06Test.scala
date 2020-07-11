package impatient_exercises

import java.io.ByteArrayOutputStream

import impatient_exercises.Chapter06.Conversions
import impatient_exercises.Chapter06._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Chapter06Test extends AnyFlatSpec with Matchers {

  val Tolerance = 0.001d

  behavior of "Conversions"

  it should "convert inches to correct centimeter value" in {
    Conversions.inchesToCentimeters(1) shouldEqual (2.54d +- Tolerance)
    Conversions.inchesToCentimeters(1.00001) shouldEqual (2.5400254d +- Tolerance)
    Conversions.inchesToCentimeters(13423432) shouldEqual (34095517.28d +- Tolerance)
  }

  it should "convert gallons to correct liter value" in {
    Conversions.gallonsToLiters(1) shouldEqual (3.785d +- Tolerance)
    Conversions.gallonsToLiters(1.00001) shouldEqual (3.78503785d +- Tolerance)
    Conversions.gallonsToLiters(13423432) shouldEqual (50807690.12d +- Tolerance)
  }

  it should "convert miles to correct kilometer value" in {
    Conversions.milesToKilometers(1) shouldEqual (1.609344d +- Tolerance)
    Conversions.milesToKilometers(1.00001) shouldEqual (1.60936009344d +- Tolerance)
    Conversions.milesToKilometers(13423432) shouldEqual (21602919.748608d +- Tolerance)
  }

  behavior of "UnitConversion"

  it should "convert inches to correct centimeter value" in {
    InchesToCentimeters(1) shouldEqual (2.54d +- Tolerance)
    InchesToCentimeters(1.00001) shouldEqual (2.5400254d +- Tolerance)
    InchesToCentimeters(13423432) shouldEqual (34095517.28d +- Tolerance)
  }

  it should "convert gallons to correct liter value" in {
    GallonsToLiters(1) shouldEqual (3.785d +- Tolerance)
    GallonsToLiters(1.00001) shouldEqual (3.78503785d +- Tolerance)
    GallonsToLiters(13423432) shouldEqual (50807690.12d +- Tolerance)
  }

  it should "convert miles to correct kilometer value" in {
    MilesToKilometers(1) shouldEqual (1.609344d +- Tolerance)
    MilesToKilometers(1.00001) shouldEqual (1.60936009344d +- Tolerance)
    MilesToKilometers(13423432) shouldEqual (21602919.748608d +- Tolerance)
  }

  behavior of "Point"

  it should "allow construction via companion object like so Point(x,y)" in {
    val point = Point(1.02,2.04)
    point.x shouldEqual 1.02
    point.y shouldEqual 2.04
  }

  behavior of "Reverse"

  it should "print command line arguments backwards" in {
    val out = new ByteArrayOutputStream
      Console.withOut(out){
        new Reverse().main(Array("*Hello","World*"))
        out.toString shouldEqual "World* *Hello"
      }
  }

  behavior of "PlayingCards"

  it should "generate proper strings for suit of card" in {
    PlayingCards.Hearts.toString shouldEqual "♥"
    PlayingCards.Spades.toString shouldEqual "♠"
    PlayingCards.Diamonds.toString shouldEqual "♦"
    PlayingCards.Clubs.toString shouldEqual "♣"
  }

  it should "return true for isRed if suit is red and false otherwise" in {
    PlayingCards.isRed(PlayingCards.Hearts) shouldEqual true
    PlayingCards.isRed(PlayingCards.Spades) shouldEqual false
    PlayingCards.isRed(PlayingCards.Diamonds) shouldEqual true
    PlayingCards.isRed(PlayingCards.Clubs) shouldEqual false
  }

  behavior of "ColorCube"

  it should "have proper ids set for each corner of color cube"in {
    ColorCube.Black.id shouldEqual 0x000000
    ColorCube.Blue.id shouldEqual 0x0000ff
    ColorCube.Red.id shouldEqual 0xff0000
    ColorCube.Magenta.id shouldEqual 0xff00ff
    ColorCube.Green.id shouldEqual 0x00ff00
    ColorCube.Cyan.id shouldEqual 0x00ffff
    ColorCube.Yellow.id shouldEqual 0xffff00
    ColorCube.White.id shouldEqual 0xffffff
  }

}
