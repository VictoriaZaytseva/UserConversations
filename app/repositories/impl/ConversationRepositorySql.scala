package repositories.impl

import models.{Conversation, User}
import repositories.{ConversationRepository, Repository}

import scala.concurrent.Future
import scala.util.Try

/**
  * Created by victoria on 21/08/16.
  */
class ConversationRepositorySql extends ConversationRepository with Repository[Conversation]{
  val table = "conversations"

  val qMarks = ""

  val findById = ""
  val update = ""

  override def constructor(): Conversation = {}

  override def findById(userId: Int, conversationId: String): Future[Try[Int]] = {
    queryOne(findById, Seq[Any]())
  }
  override def update(id: Int): Future[Try[Conversation]] = {
    queryOne(update, Seq[Any]())
  }
}
