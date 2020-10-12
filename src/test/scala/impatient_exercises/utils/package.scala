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

  def time[R](block: => R)(name: String = ""): Long = {
    val t0 = System.nanoTime()
    val result = block
    val t1 = System.nanoTime()
    println(s"Elapsed time ${ if(!name.isEmpty) s"{${name}}" }=" + (t1 - t0) + "ns")
    (t1 - t0)
  }
}
