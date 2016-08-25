package repositories

import com.github.mauricio.async.db.Connection
import models.{Conversation, User}

import scala.concurrent.Future
import scala.util.Try

/**
  * Created by victoria on 21/08/16.
  */
trait ConversationRepository {
  def findById(userId: Int, conversationId: Int)(implicit conn: Connection): Future[Try[Conversation]]
  def update(id: Int)(implicit conn: Connection): Future[Try[User]]

}
