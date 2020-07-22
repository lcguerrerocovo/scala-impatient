package impatient_exercises

import java.io.{ByteArrayOutputStream, File, FileWriter, PrintWriter}

import org.scalacheck.Gen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

import scala.io.Source

class Chapter09Test extends AnyFlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  def withFile(testCode: (File, FileWriter) => Any) {
    val file = File.createTempFile("file", ".out") // create the fixture
    val writer = new FileWriter(file)
    try {
      testCode(file, writer)
    }
    finally {
      new File(file.getAbsolutePath).delete()
    }
  }

  behavior of "reverseFile"

  it should "reverse a file's contents" in withFile { (file, writer) =>
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
    withFile { (file, writer) =>

      val contents = "1\t2\t3\t4\t5"
      writer.write(contents)
      writer.close()

      Chapter09.tabsToSpaces(file.getAbsolutePath,6)
      Source.fromFile(file.getAbsolutePath, "UTF-8").mkString shouldEqual
        "1      2      3      4      5"
  }

  behavior of "printWords"

  it should "print all words in contents of file with more than 12 characters" in
    withFile { (file, writer) =>
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
    withFile { (file, writer) =>
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
    withFile { (file, writer) =>
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
    withFile { (file, writer) =>
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
    withFile { (file, writer) =>
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
}
