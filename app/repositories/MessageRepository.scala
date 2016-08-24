package repositories

import models.Message

import scala.concurrent.Future
import scala.util.Try

/**
  * Created by victoria on 21/08/16.
  */
trait MessageRepository {
  def create(message: Message): Future[Try[Message]]
  def getByConversationId(userId: Int, conversationId: Int): Future[Try[IndexedSeq[Message]]]
}
