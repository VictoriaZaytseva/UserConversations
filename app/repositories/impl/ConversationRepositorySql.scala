package repositories.impl

import com.github.mauricio.async.db.{Connection, RowData}
import fail.{RepositoryError}
import models.{Conversation, User}
import org.joda.time.DateTime
import org.omg.CORBA.LongHolder
import repositories.{ConversationRepository, Repository}

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.\/
/**
  * Created by victoria on 21/08/16.
  */
class ConversationRepositorySql extends ConversationRepository with Repository[Conversation]{
  val table = "conversations"

  val qMarks = ""

  val findById = s"""select * from conversation where id = ?"""
  val update = "update conversations set message_count = ? where id = ?"

  override def constructor(row: RowData): Conversation = Conversation(
    id = row("id").asInstanceOf[Integer],
    creator = row("creator_id").asInstanceOf[Integer],
    createdAt = row("create_at").asInstanceOf[DateTime],
    messageCount = row("message_count").asInstanceOf[Int])

  override def findById(conversationId: Int)(implicit conn: Connection): Future[\/[RepositoryError.Fail, Conversation]] = {
    queryOne(findById, Seq[Any](conversationId))
  }
  override def update(id: Int, messageCount: Int)(implicit conn: Connection): Future[\/[RepositoryError.Fail, Conversation]] = {
    queryOne(update, Seq[Any](messageCount, id))
  }
}
