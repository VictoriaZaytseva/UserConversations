package repositories

import com.github.mauricio.async.db.Connection
import models.User

import scala.concurrent.Future
import scala.util.Try

/**
  * Created by victoria on 21/08/16.
  */
trait UserRepository {
  def findById(id: Int)(implicit conn: Connection): Future[Try[User]]
}
