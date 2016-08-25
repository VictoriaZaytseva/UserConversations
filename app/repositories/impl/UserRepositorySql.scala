package repositories.impl

import com.github.mauricio.async.db.Connection
import models.User
import repositories.{Repository, UserRepository}

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by victoria on 21/08/16.
  */
class UserRepositorySql extends UserRepository with Repository[User]{
  val table = "users"

  val qMarks = ""

  val SelectOneById = ""
  override def constructor(): User = {
  }
  override def findById(id: Int)(implicit conn: Connection): Future[Try[User]] = {
    queryOne(SelectOneById, Seq[Any](id))
  }
}
