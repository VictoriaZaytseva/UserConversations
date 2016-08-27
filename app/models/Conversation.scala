package models

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Conversation(id: Int, creator: Int, createdAt: DateTime, messageCount: Int, messages: IndexedSeq[Message] = IndexedSeq())

//companion object to return json
object Conversation {

  implicit val conversationWrites: Writes[Conversation] = (
    (__ \ "id").write[Int] and
      (__ \ "creator").write[Int] and
      (__ \ "createdAt").write[DateTime] and
      (__ \ "messageCount").write[Int] and
      (__ \ "messages").write[IndexedSeq[Message]]
    )(unlift(Conversation.unapply))

}