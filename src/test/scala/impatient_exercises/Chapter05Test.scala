package impatient_exercises

import impatient_exercises.Chapter05.{BankAccount, BetterPerson, BetterTime, Counter, Person,
  Time, Car}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class Chapter05Test extends AnyFlatSpec with Matchers {

  behavior of "Counter"

  it should "throw exception if incrementing over integer max value" in {
    val counter = new Counter(Int.MaxValue)
    assertThrows[Exception](counter.increment())
  }

  behavior of "BankAccount"

  it should "return current balance" in {
    val bankAccount = new BankAccount()
    bankAccount.deposit(10000)
    assert(bankAccount.balance == 10000)
  }

  it should "throw exception if withdrawing more than balance" in {
    val bankAccount = new BankAccount()
    bankAccount.deposit(10000)
    assertThrows[Exception](bankAccount.withdraw(20000))
  }

  behavior of "Time"

  it should "throw exception if time is set outside of range" in {
    assertThrows[Exception](new Time(24, 59))
    assertThrows[Exception](new Time(23, 60))
  }

  it should "should have public accessors for hours and minutes" in {
    val time = new Time(23, 59)
    assert(time.hours == 23)
    assert(time.minutes == 59)
  }

  it should "check if other time is before time" in {
    val time = new Time(23, 59)
    time.before(new Time(23, 58)) shouldEqual true
    time.before(new Time(23, 59)) shouldEqual false
  }

  behavior of "BetterTime"

  it should "throw exception if time is set outside of range" in {
    assertThrows[Exception](new BetterTime(24, 59))
    assertThrows[Exception](new BetterTime(23, 60))
  }

  it should "should have public accessors for hours and minutes" in {
    val time = new BetterTime(23, 59)
    assert(time.hours == 23)
    assert(time.minutes == 59)
  }

  it should "check if other time is before time" in {
    val time = new BetterTime(23, 59)
    time.before(new BetterTime(23, 58)) shouldEqual true
    time.before(new BetterTime(23, 59)) shouldEqual false
  }

  behavior of "Person"

  it should "set age to 0 if negative" in {
    new Person(-13).getAge shouldEqual 0
  }

  behavior of "BetterPerson"

  it should "have parse fullName and expose fields for first and last name" in {
    val bp = new BetterPerson("mister hacker")
    bp.firstName shouldEqual "mister"
    bp.lastName shouldEqual "hacker"
  }

  behavior of "Car"

  it should "have four constructors and initialize fields properly" in {
    var car = new Car("ferrari", "testarrosa")
    car.manufacturer shouldEqual "ferrari"
    car.modelName shouldEqual "testarrosa"
    car.modelYear shouldEqual -1
    car.licensePlate shouldEqual ""

    car = new Car("ferrari", "testarrosa", 1999)
    car.manufacturer shouldEqual "ferrari"
    car.modelName shouldEqual "testarrosa"
    car.modelYear shouldEqual 1999
    car.licensePlate shouldEqual ""

    car = new Car("ferrari", "testarrosa", licensePlate = "VXZ999")
    car.manufacturer shouldEqual "ferrari"
    car.modelName shouldEqual "testarrosa"
    car.modelYear shouldEqual -1
    car.licensePlate shouldEqual "VXZ999"

    car = new Car("ferrari", "testarrosa", 1999, "VXZ999")
    car.manufacturer shouldEqual "ferrari"
    car.modelName shouldEqual "testarrosa"
    car.modelYear shouldEqual 1999
    car.licensePlate shouldEqual "VXZ999"
  }

  it should "have a read write property for license plate" in {
    val car = new Car("ferrari", "testarrosa", 1999, "VXZ999")
    car.licensePlate = "VXY987"
    car.manufacturer shouldEqual "ferrari"
    car.modelName shouldEqual "testarrosa"
    car.modelYear shouldEqual 1999
    car.licensePlate shouldEqual "VXY987"
  }

}
