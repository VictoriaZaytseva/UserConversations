package models

import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._
/**
  * Created by victoria on 20/08/16.
  */
case class Conversation (
                          id: Int,
                          creator: Int,
                          createdAt: DateTime,
                          messageCount: Int,
                          messages: IndexedSeq[Message] = IndexedSeq())


object Conversation {
  implicit val conversationWrites: Writes[Conversation] = (
    (__ \ "id").write[Int] and
      (__ \ "creator").write[Int] and
      (__ \ "createdAt").write[DateTime] and
      (__ \ "messageCount").write[Int] and
      (__ \ "messages").write[IndexedSeq[Message]]
    )(unlift(Conversation.unapply))
}