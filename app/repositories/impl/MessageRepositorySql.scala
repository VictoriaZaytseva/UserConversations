package repositories.impl

import com.github.mauricio.async.db.{Connection, RowData}
import models.{Message, User}
import repositories.{MessageRepository, Repository}

import scala.concurrent.Future
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by victoria on 21/08/16.
  */
class MessageRepositorySql extends MessageRepository with Repository[Message]{

  val table = "messages"

  val qMarks = ""

  val findByConversationId = ""

  val Insert = ""

//  CREATE	TABLE	 messages	(
//    id	serial primary key,
//    text	text,
//    created_at	timestamp with time zone	DEFAULT	NULL,
//  sender int references users(id) on update CASCADE,
//  recipient	int references users(id) on update CASCADE,
//  conversation_id 	int references conversation(id) on update CASCADE);
  /**
    * id: Int,
                   text: String,
                   sender: Int,
                   recepient: Int,
                   conversationId: Int)
    * @param row
    * @return
    */
  override def constructor(row: RowData): Message = Message(
    id = row("id").asInstanceOf[Int],
    text = row("text").asInstanceOf[String],
    sender = row("sender").asInstanceOf[Int],
    recipient = row("recipient").asInstanceOf[Int],
    conversationId = row("conversation_id").asInstanceOf[Int]
  )

  override def create(message: Message)(implicit conn: Connection): Future[Try[Message]] = {
    queryOne(Insert, Seq[Any]())
  }

  override def getByConversationId(userId: Int, conversationId: Int)(implicit conn: Connection): Future[Try[IndexedSeq[Message]]] = {
    queryList(findByConversationId, Seq[Any](userId, conversationId))
  }

}