package impatient_exercises

import java.util
import java.util.Map

import scala.collection.mutable

/**
 * === solution to Chapter 7 exercise 1  ===
 *
 * 1. Write an example program to demonstrate that
 *   package com.horstmann.impatient
 * is not the same as
 *   package com
 *   package horstmann
 *   package impatient
 */

package com {
  object Person {
    val name = "Fredrick"
  }
  package horstmann {
    object Book {
      val name = "Thus Spoke Zarathustra"
    }
  }
}

/**
 * the following form is same as top of file
 *   package com
 *   package horstmann
 *   package impatient
 */
package com {
  package horstmann {
    package impatient {
      class Publication {
        // com and com.horstmann are accesible here because of nesting in packages
        val entry = "author:" + Person.name + "/publication:" + Book.name
      }
    }
  }
}

/**
 * the following form is same as top of file
 *   package com.horstmann.impatient
 */
package com.hosrtmann.impatient {
  class Publication2 {
    // com and com.horstmann are not accesible here so following statement would not compile
    // val entry = "author:" + Person.name + "/publication:" + Book.name
  }
}

object Chapter07 {

  /**
   * 3. Write a package random with functions nextInt(): Int, nextDouble(): Double, and
   * setSeed(seed: Int): Unit. To generate random numbers, use the linear congruential generator
   *  next = (previous × a + b) mod 2^n
   * where a = 1664525, b = 1013904223, n = 32, and the initial value of previous seed
   *
   * @see [[impatient_exercises.random]]
   */
  class Random

  /**
   * 4. Why do you think the Scala language designers provided the package object syntax instead of
   *    simply letting you add functions and variables to a package?
   *
   *    for interoperability with Java, the JVM has the limitation of not allowing definitions of
   *    functions of variables in packages so scala packages do not allow for that as well
   */

  /**
   * 5. What is the meaning of private[com] def giveRaise(rate: Double)? Is it useful?
   *
   *   it makes the giveRaise method accesible from all com.* packages, it does not seem useful
   *   since the com package is used as standard naming notation for most scala programs and
   *   would lead to programs with similarly named methods not compiling unless developers
   *   carefully import the right method
   */

  /**
   * ===A class that provides a solution to Chapter 7 exercise 6 and 7  ===
   *
   * 6. Write a program that copies all elements from a Java hash map into a Scala
   * hash map. Use imports to rename both classes.
   */
  object MapConverter {
    import java.util.{HashMap => JavaHashMap}
    import collection.mutable.{HashMap => ScalaHashMap}

    def copy[K,V](map: JavaHashMap[K,V]): ScalaHashMap[K,V] = {
      val entrySet = map.entrySet().iterator()
      val scalaMap = ScalaHashMap[K,V]()
      while(entrySet.hasNext()) {
        val kv = entrySet.next()
        scalaMap += (kv.getKey -> kv.getValue)
      }
      scalaMap
    }
  }

}
