package impatient_exercises

import impatient_exercises.Chapter05.{BankAccount, BetterTime, Counter, Time}
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
}
