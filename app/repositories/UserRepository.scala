package repositories

import com.github.mauricio.async.db.Connection
import fail.{RepositoryError}
import models.User

import scala.concurrent.Future
import scala.util.Try
import scalaz.\/

/**
  * Created by victoria on 21/08/16.
  */
trait UserRepository {
  def findById(id: Int)(implicit conn: Connection): Future[\/[RepositoryError.Fail, User]]
}
