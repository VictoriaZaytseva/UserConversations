package repositories.impl

import com.github.mauricio.async.db.{Connection, RowData}
import models.User
import org.joda.time.DateTime
import repositories.{Repository, UserRepository}

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by victoria on 21/08/16.
  */
class UserRepositorySql extends UserRepository with Repository[User]{

  val table = "users"

  val SelectOneById = s"""select * from users where id = ?"""

  override def constructor(row: RowData): User = User(id = row("id").asInstanceOf[Int], username = row("username").asInstanceOf[String], fullname = row("full_name").asInstanceOf[String], age = row("age").asInstanceOf[Int])

  override def findById(id: Int)(implicit conn: Connection): Future[Try[User]] = {
    queryOne(SelectOneById, Seq[Any](id))
  }
}
