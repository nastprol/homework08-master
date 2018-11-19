package fintech.homework08

import java.time.LocalDate
import PeopleApp._
import org.scalatest.{FlatSpec, Matchers}

class DBResTest extends FlatSpec with Matchers {

  def app: DBRes[Person] = for {
    _ <- setup()
    old <- getOldPerson()
    clone <- clonePerson(old)
  } yield clone

  val result: Person = DBRes(app.run).execute(uri)

  "Result" should "be correct" in {
    result should be(Person("Alice", LocalDate.now()))
  }

  "flatMap Test" should "work correct" in {

    val app = for {
      _ <- setup()
      old <- getOldPerson()
      _ <- clonePerson(old)
    } yield ()

    val res = app
      .flatMap(_=> DBRes.select("SELECT * FROM people", List.empty)(readPerson))
      .execute(uri)

    val a = Person("Alice", LocalDate.of(1970, 1, 1))
    val b = Person("Bob", LocalDate.of(1981, 5, 12))
    val c = Person("Charlie", LocalDate.of(1979, 2, 20))

    res should be(List(a, b, c, a.copy(birthday = LocalDate.now())))
  }
}







