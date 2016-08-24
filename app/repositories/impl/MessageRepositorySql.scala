package repositories.impl

import models.{Message, User}
import repositories.{MessageRepository, Repository}

import scala.concurrent.Future
import scala.util.Try

/**
  * Created by victoria on 21/08/16.
  */
class MessageRepositorySql extends MessageRepository with Repository[Message]{

  val table = "messages"

  val qMarks = ""

  val findByConversationId = ""

  val Insert = ""

  override def constructor(): Message = {}
  override def create(message: Message): Future[Try[Message]] = {
    queryOne(Insert, Seq[Any]())
  }

  override def getByConversationId(userId: Int, conversationId: Int): Future[Try[IndexedSeq[Message]]] = {
    queryList(findByConversationId, Seq[Any](userId, conversationId))
  }

}
