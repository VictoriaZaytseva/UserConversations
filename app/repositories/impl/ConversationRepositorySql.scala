package repositories.impl

import com.github.mauricio.async.db.{Connection, RowData}
import fail.{RepositoryError}
import models.{Conversation, User}
import org.joda.time.DateTime
import repositories.{ConversationRepository, Repository}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalaz.\/
/**
  * A class to work with conversation table
  */
class ConversationRepositorySql extends ConversationRepository with Repository[Conversation]{
  //queries
  val findById = s"""select * from conversation where id = ?"""

  val update = "update conversation set message_count = ? where id = ? returning id, creator_id, create_at, message_count"

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
