package repositories

import com.github.mauricio.async.db.Connection
import fail.RepositoryError
import models.{Conversation, Message, User}

import scala.concurrent.Future
import scala.util.Try
import scalaz.\/

/**
  * Created by victoria on 21/08/16.
  */
trait ConversationRepository {
  def findById(id: Int)(implicit conn: Connection): Future[\/[RepositoryError.Fail, Conversation]]
  def update(id: Int, messageCount: Int)(implicit conn: Connection): Future[\/[RepositoryError.Fail, Conversation]]
}
