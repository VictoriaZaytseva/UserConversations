package models

import play.api.libs.functional.syntax._
import play.api.libs.json._
/**
  * Created by victoria on 20/08/16.
  */
case class Message(id: Int = 0, text: String, sender: Int, recipient: Int, conversationId: Int)
object Message {
  implicit val messageWrites: Writes[Message] = (
    (__ \ "id").write[Int] and
      (__ \ "text").write[String] and
      (__ \ "sender").write[Int] and
      (__ \ "recepient").write[Int] and
      (__ \ "conversationId").write[Int]
    )(unlift(Message.unapply))
}