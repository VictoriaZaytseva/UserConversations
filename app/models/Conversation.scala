package models

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._
/**
  * Created by victoria on 20/08/16.
  */
case class Conversation (
                         conversationId: Int,
                         creator: Int,
                         creationTime: DateTime,
                         messageCount: Int,
                         messages: IndexedSeq[Message])


object Conversation {
  implicit val conversationWrites: Writes[Conversation] = (
    (__ \ "conversationId").write[Int] and
      (__ \ "creator").write[Int] and
      (__ \ "creationTime").write[DateTime] and
      (__ \ "messageCount").write[Int] and
      (__ \ "messages").write[IndexedSeq[Message]]
    )(unlift(Conversation.unapply))
}