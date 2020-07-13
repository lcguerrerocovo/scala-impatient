package impatient_exercises


import impatient_exercises.Chapter07.MapConverter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Chapter07Test extends AnyFlatSpec with Matchers {

  behavior of "random"

  it should "generate a random number (Double)" in {
    random.nextDouble() shouldEqual 1.013904223E9
  }

  it should "generate a random number (Int)" in {
    random.setSeed(429988)
    random.nextInt() shouldEqual -519858509
  }

  behavior of "MapConverter"

  it should "copy a Java HashMap to a Scala HashMap" in {
    import java.util.{HashMap => JavaHashMap}

    val map: JavaHashMap[String,Int] = new java.util.HashMap[String, Int]()
    map.put("entry1",1)
    map.put("entry2",2)
    map.put("entry3",3)

    MapConverter.copy(map) should contain allOf (("entry1" -> 1), ("entry2" -> 2), ("entry3" -> 3))
  }

}
