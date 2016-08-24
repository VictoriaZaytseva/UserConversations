package repositories.impl

import models.User
import repositories.{Repository, UserRepository}

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
  * Created by victoria on 21/08/16.
  */
class UserRepositorySql extends UserRepository with Repository[User]{
  val table = "users"

  val qMarks = ""

  val SelectOneById = ""
  override def constructor(): User = {
  }
  override def findById(id: Int): Future[Try[User]] = {
    queryOne(SelectOneById, Seq[Any](id))
  }
}
