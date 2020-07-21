package impatient_exercises

import java.io.{File, FileWriter, PrintWriter}

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
}