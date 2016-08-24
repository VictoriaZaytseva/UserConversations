package repositories

import models.User

import scala.concurrent.Future
import scala.util.Try

/**
  * Created by victoria on 21/08/16.
  */
trait UserRepository {
  def findById(id: Int): Future[Try[User]]
}
