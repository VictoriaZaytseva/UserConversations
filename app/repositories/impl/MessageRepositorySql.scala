package repositories.impl

import com.github.mauricio.async.db.{Connection, RowData}
import models.{Message, User}
import org.joda.time.DateTime
import repositories.{MessageRepository, Repository}

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by victoria on 21/08/16.
  */
class MessageRepositorySql extends MessageRepository with Repository[Message]{

  val table = "messages"

  val findByConversationId = s"""select * from messages where conversation_id = ?"""

  val Insert = "insert into messages(text, created_at, sender, recipient, conversation_id) values(?, ?, ?, ?, ?)"
//insert into messages(text, created_at, sender, recipient, conversation_id) values('hey!', CURRENT_TIMESTAMP, 1, 3, 1)
  override def constructor(row: RowData): Message = Message(
    id = row("id").asInstanceOf[Int],
    text = row("text").asInstanceOf[String],
    sender = row("sender").asInstanceOf[Int],
    recipient = row("recipient").asInstanceOf[Int],
    conversationId = row("conversation_id").asInstanceOf[Int]
  )

  override def create(message: Message)(implicit conn: Connection): Future[Try[Message]] = {
    queryOne(Insert, Seq[Any](message.text, new DateTime(), message.sender, message.recipient, message.conversationId))
  }

  override def getByConversationId(conversationId: Int)(implicit conn: Connection): Future[Try[IndexedSeq[Message]]] = {
    queryList(findByConversationId, Seq[Any](conversationId))
  }

}