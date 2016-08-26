package repositories

import com.github.mauricio.async.db.Connection
import fail.{RepositoryError}
import models.Message

import scala.concurrent.Future
import scala.util.Try
import scalaz.\/

/**
  * Created by victoria on 21/08/16.
  */
trait MessageRepository {
  def create(message: Message)(implicit conn: Connection): Future[\/[RepositoryError.Fail, Message]]
  def getByConversationId(conversationId: Int)(implicit conn: Connection): Future[\/[RepositoryError.Fail, IndexedSeq[Message]]]
}
