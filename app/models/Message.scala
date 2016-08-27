package models

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Message(id: Int = 0, text: String, sender: Int, recipient: Int, conversationId: Int)
//companion object to return json
object Message {

  implicit val messageWrites: Writes[Message] = (
    (__ \ "id").write[Int] and
      (__ \ "text").write[String] and
      (__ \ "sender").write[Int] and
      (__ \ "recepient").write[Int] and
      (__ \ "conversationId").write[Int]
    )(unlift(Message.unapply))

}