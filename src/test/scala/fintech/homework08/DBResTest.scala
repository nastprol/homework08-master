package fintech.homework08

import java.time.LocalDate
import PeopleApp._

import org.scalatest.{FlatSpec, Matchers}

class DBResTest extends FlatSpec with Matchers {

  def program: DBRes[Person] = {
    for {
      _ <- setup()
      old <- getOldPerson()
      clone <- clonePerson(old)
    } yield clone
  }

  val result: Person = DBRes(program.run).execute(uri)

  "Result" should "be correct" in {
    result should be(Person("Alice", LocalDate.now()))
  }
}