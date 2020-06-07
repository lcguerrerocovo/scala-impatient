package impatient_exercises

object Chapter05 {

  /**
   * ===A class that provides a solution to Chapter 5 exercise 1  ===
   *
   * 1. Improve the Counter class in Section 5.1, “Simple Classes and Parameterless Methods,”
   * on page 55 so that it doesn’t turn negative at Int.MaxValue.
   */
  class Counter {
    private var value = 0

    def this(value: Int) {
      this()
      this.value = value
    }

    def increment() {
      if(value == Int.MaxValue)
        throw new Exception("trying to increment counter over maxium value for Int")
      else value += 1
    }
    def current() = value
  }

  /**
   * ===A class that provides a solution to Chapter 5 exercise 2  ===
   *
   * 2. Write a class BankAccount with methods deposit and withdraw, and a read-only property balance.
   */
  class BankAccount {
    private var currentBalance: Double = 0

    def deposit(amount: Double) = {
      currentBalance += amount
    }

    def withdraw(amount: Double) = {
      if(currentBalance - amount < 0)
        throw new Exception("trying to withdraw more than existing balance")
      else currentBalance -= amount
    }

    def balance(): Double = this.currentBalance
  }

  /**
   * ===A class that provides a solution to Chapter 5 exercise 3  ===
   *
   * 3. Write a class Time with read-only properties hours and minutes and a method before
   * (other: Time): Boolean that checks whether this time comes before the other. A Time object
   * should be constructed as new Time(hrs, min), where hrs is in military time format (between 0
   * and 23).
   *
   * @param hours
   * @param minutes
   */
  class Time(val hours: Int, val minutes: Int) { self =>
    if(this.hours > 23 || this.hours < 0)
      throw new Exception("time must be in military format (0-23)")
    if(this.minutes > 59 || this.minutes < 0)
      throw new Exception("minutes must be within range (0-59)")

    def before(other: Time) = {
      (self.hours > other.hours
        || (self.hours == other.hours && self.minutes > other.minutes))
    }

  }

  /**
   * ===A class that provides a solution to Chapter 5 exercise 4  ===
   *
   * 4. Reimplement the Time class from the preceding exercise so that the internal representation
   * is the number of minutes since midnight (between 0 and 24 × 60 – 1). Do not change the public
   * interface. That is, client code should be unaffected by your change.
   */
  class BetterTime(val hours: Int, val minutes: Int) { self =>
    if(this.hours > 23 || this.hours < 0)
      throw new Exception("time must be in military format (0-23)")
    if(this.minutes > 59 || this.minutes < 0)
      throw new Exception("minutes must be within range (0-59)")

    private var minutesFromMidnight = (this.hours * 60) + this.minutes

    def before(other: BetterTime) =
      self.minutesFromMidnight > other.minutesFromMidnight
  }

  /**
   * ===A class that provides a solution to Chapter 5 exercise 6  ===
   *
   * 6. In the Person class of Section 5.1, “Simple Classes and Parameterless Methods,” on page 55,
   * provide a primary constructor that turns negative ages to 0.
   */
  class Person(var age: Int) {
    if(age < 0) age = 0

    def getAge: Int = age

    def setAge(age: Int): Unit = {
      this.age = age
    }
  }

  /**
   * ===A class that provides a solution to Chapter 5 exercise 7  ===
   *
   * 7. Write a class Person with a primary constructor that accepts a string containing a first
   * name, a space, and a last name, such as new Person("Fred Smith"). Supply read-only properties
   * firstName and lastName. Should the primary constructor parameter be a var, a val, or a plain
   * parameter? Why?
   */
  class BetterPerson(fullName: String) {
    val (firstName, lastName) = fullName.split(" ").take(2) match {
      case Array(firstName, lastName) => (firstName, lastName)
      case _ => throw new Exception("fullName must contain first name and last name separated by " +
        "space")
    }
  }

  /**
   * ===A class that provides a solution to Chapter 5 exercise 8  ===
   *
   * 8. Make a class Car with read-only properties for manufacturer, model name, and model year,
   * and a read-write property for the license plate. Supply four constructors. All require the
   * manufacturer and model name. Optionally, model year and license plate can also be specified
   * in the constructor. If not, the model year is set to -1 and the license plate to the empty
   * string. Which constructor are you choosing as the primary constructor? Why?
   */

  class Car(val manufacturer: String, val modelName: String, val modelYear: Int, var
  licensePlate: String) {

    def this(manufacturer: String, modelName: String) {
      this(manufacturer, modelName, -1, "")
    }

    def this(manufacturer: String, modelName: String, modelYear: Int) {
      this(manufacturer, modelName, modelYear, "")
    }

    def this(manufacturer: String, modelName: String, licensePlate: String) {
      this(manufacturer, modelName, -1, licensePlate)
    }
  }
}
