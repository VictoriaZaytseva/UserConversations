package repositories

import com.github.mauricio.async.db.Connection
import models.Message

import scala.concurrent.Future
import scala.util.Try

/**
  * Created by victoria on 21/08/16.
  */
trait MessageRepository {
  def create(message: Message)(implicit conn: Connection): Future[Try[Message]]
  def getByConversationId(userId: Int, conversationId: Int)(implicit conn: Connection): Future[Try[IndexedSeq[Message]]]
}
