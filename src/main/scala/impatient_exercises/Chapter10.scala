// Example: Chapter 10: Traits

package impatient_exercises

import java.awt.Rectangle

object Chapter10 {

  // **1.The java.awt.Rectangle class has useful methods translate and grow that are unfortunately
  //     absent from classes such as java.awt.geom.Ellipse2D. In Scala, you can fix this problem.
  //     Define a trait RectangleLike with concrete methods translate and grow. Provide any
  //     abstract methods that you need for the implementation, so that you can mix in the trait
  //     like this:**
  //
  //     ```
  //     val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
  //     egg.translate(10, -10)
  //     egg.grow(10, 20)
  //     ```

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


}
