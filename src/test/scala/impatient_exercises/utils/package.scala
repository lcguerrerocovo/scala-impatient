package impatient_exercises

import java.io.{File, FileWriter}

package object utils {

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
}
