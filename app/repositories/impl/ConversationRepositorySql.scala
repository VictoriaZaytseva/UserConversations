package repositories.impl

import com.github.mauricio.async.db.Connection
import models.{Conversation, User}
import repositories.{ConversationRepository, Repository}

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by victoria on 21/08/16.
  */
class ConversationRepositorySql extends ConversationRepository with Repository[Conversation]{
  val table = "conversations"

  val qMarks = ""

  val findById = ""
  val update = ""

  override def constructor(): Conversation = {}

  override def findById(userId: Int, conversationId: Int)(implicit conn: Connection): Future[Try[Conversation]] = {
    queryOne(findById, Seq[Any]())
  }
  override def update(id: Int)(implicit conn: Connection): Future[Try[Conversation]] = {
    queryOne(update, Seq[Any]())
  }
}
