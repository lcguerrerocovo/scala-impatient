package impatient_exercises

import java.io.{ByteArrayOutputStream, File, FileInputStream, ObjectInputStream}
import java.nio.file.Files

import org.scalacheck.Gen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

class Chapter09Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "reverseFile"

  it should "reverse a file's contents" in utils.withFile { (file, writer) =>
    val contents = """these are the file's contents
                     |it has several lines
                     |and it should be reversed by reverseFile""".stripMargin
    writer.write(contents)
    writer.close()

    Chapter09.reverseFile(file.getAbsolutePath)
    Source.fromFile(file.getAbsolutePath, "UTF-8").getLines().toArray.head shouldEqual
      "and it should be reversed by reverseFile"
  }

  behavior of "tabsToSpaces"

  it should "replace all tab characters with specified number of spaces" in
    utils.withFile { (file, writer) =>

      val contents = "1\t2\t3\t4\t5"
      writer.write(contents)
      writer.close()

      Chapter09.tabsToSpaces(file.getAbsolutePath,6)
      Source.fromFile(file.getAbsolutePath, "UTF-8").mkString shouldEqual
        "1      2      3      4      5"
  }

  behavior of "printWords"

  it should "print all words in contents of file with more than 12 characters" in
    utils.withFile { (file, writer) =>
      val contents = """thesearethefile's contents
                       |ithasseverallineswithlongwords that should be parsed
                       |andthelongwordsshould be printedtotheconsoleoutput""".stripMargin
      writer.write(contents)
      writer.close()

      val out = new ByteArrayOutputStream
      Console.withOut(out) {
        Chapter09.printWords(file.getAbsolutePath, 13)
        out.toString shouldEqual """thesearethefile's
                          |ithasseverallineswithlongwords
                          |andthelongwordsshould
                          |printedtotheconsoleoutput
                          |""".stripMargin
      }
    }

  behavior of "numberStats"

  it should "prints sum, average, min and max of floating point numbers in file" in
    utils.withFile { (file, writer) =>
      val contents = """4.5 6.7 8.985 34.56 78.56 0.01""".stripMargin
      writer.write(contents)
      writer.close()

      val out = new ByteArrayOutputStream
      Console.withOut(out) {
        Chapter09.numberStats(file.getAbsolutePath)
        out.toString shouldEqual """133.315
                                   |22.219166666666666
                                   |78.56
                                   |0.01
                                   |""".stripMargin
      }
    }

  behavior of "powersAndReciprocals"

  it should "write a table to a file with powers and reciprocals of base 2 with exponent from 0 " +
    "to 20" in
    utils.withFile { (file, writer) =>
      Chapter09.powersAndReciprocals(file.getAbsolutePath,2,0 to 20)
      Source.fromFile(file.getAbsolutePath, "UTF-8").mkString shouldEqual
      """1         1.0
        |2         0.5
        |4         0.25
        |8         0.125
        |16        0.0625
        |32        0.03125
        |64        0.015625
        |128       0.0078125
        |256       0.00390625
        |512       0.001953125
        |1024      9.765625E-4
        |2048      4.8828125E-4
        |4096      2.44140625E-4
        |8192      1.220703125E-4
        |16384     6.103515625E-5
        |32768     3.0517578125E-5
        |65536     1.52587890625E-5
        |131072    7.62939453125E-6
        |262144    3.814697265625E-6
        |524288    1.9073486328125E-6
        |1048576   9.5367431640625E-7
        |""".stripMargin
    }

  behavior of "quotedStrings"

  it should "print all quoted strings in a file" in
    utils.withFile { (file, writer) =>
      val contents =
        """"like this, maybe with \" or \\" this is a continuation of "the example"" string
          |"from the book"""".stripMargin
      writer.write(contents)
      writer.close()

      val out = new ByteArrayOutputStream
      Console.withOut(out) {
        Chapter09.quotedStrings(file.getAbsolutePath)
        out.toString shouldEqual """"like this, maybe with \"
                                   |" this is a continuation of "
                                   |""
                                   |"from the book"
                                   |""".stripMargin
      }
    }

  behavior of "nonFloatingPoint"

  it should "print all tokens that are not a floating point number" in
    utils.withFile { (file, writer) =>
      val contents =
        """12.34 . 4. +3.5 -1.2345 -1. 45
          |token +34 .34 0.45""".stripMargin
      writer.write(contents)
      writer.close()

      val out = new ByteArrayOutputStream
      Console.withOut(out) {
        Chapter09.nonFloatingPoint(file.getAbsolutePath)
        out.toString shouldEqual """.
                                   |4.
                                   |-1.
                                   |45
                                   |token
                                   |+34
                                   |""".stripMargin
      }
    }

  behavior of "srcImgTags"

  it should "print all tokens that are not a floating point number" in {
    val out = new ByteArrayOutputStream
    Console.withOut(out) {
      Chapter09.srcImgTags("https://lcguerrerocovo.github.io/scala-impatient/")
      out.toString shouldEqual """img/scala_impatient_rabbit.jpg
                                 |""".stripMargin
    }
  }

  behavior of "recursiveClassFileCount"

  it should "count all class files walking a path recursively" in {
    val tempDir = Files.createTempDirectory("for-test")
    val otherTempDir = Files.createTempDirectory(tempDir, "one-level-deep")
    val file = Files.createTempFile(tempDir, "test", ".class")
    val otherFile = Files.createTempFile(otherTempDir, "test", ".class")
    try {
      val count = Chapter09.recursiveClassFileCount(tempDir.toFile.getAbsolutePath)
      count shouldEqual 2
    } finally {
        new File(tempDir.toFile.getAbsolutePath).delete()
        new File(otherTempDir.toFile.getAbsolutePath).delete()
        new File(file.toFile.getAbsolutePath).delete()
        new File(otherFile.toFile.getAbsolutePath).delete()
    }
  }

  behavior of "Person"

  it should "create friend relationships, write to file and verify relationships hold" in
    utils.withFile { (file, writer) =>
      import Chapter09.{Person, saveToFile}
      val p1 = new Person("fred")
      val p2 = new Person("wilma")
      val p3 = new Person("barney")
      val p4 = new Person("betty")

      p1.addFriend(p2)
      p2.addFriend(p1)
      p2.addFriend(p3)
      p3.addFriend(p2)
      val arr = new ArrayBuffer[Person]()
      arr += p1 += p2 += p3 += p4
      saveToFile(arr.toArray, file.getAbsolutePath)
      val in = new ObjectInputStream(new FileInputStream(file.getAbsolutePath))
      val persons = in.readObject().asInstanceOf[Array[Person]]

      persons(0) shouldEqual p1
      persons(1) shouldEqual p2
      persons(2) shouldEqual p3
      persons(3) shouldEqual p4
      persons(0).getFriends should contain only (p2)
      persons(1).getFriends should contain allOf (p1,p3)
      persons(2).getFriends should contain only (p2)
      persons(3).getFriends shouldBe empty
    }
}
