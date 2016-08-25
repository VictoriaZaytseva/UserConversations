package controllers

import play.api._
import play.api.mvc._
import play.libs.Json
import services.ConversationServiceDefault
import play.api.libs.functional.syntax._
import play.api.libs.json._

import scala.concurrent.Future
import scala.util.{Failure, Success}

class Application(conversationService: ConversationServiceDefault) extends Controller {

  case class MessagePostForm(recipient_id: Int, senderId: Int, message: String)

  object MessagePostForm {
    implicit val postReads = (
      (__ \ "recipient_id").read[Int] and
        (__ \ "sender_id").read[Int] and
        (__ \ "text").read[String]
      )(MessagePostForm.apply _)
  }

  def getMessages(userId: Int, conversationId: Int) = Action.async { implicit request =>
    conversationService.fetchConversation(userId, conversationId).map{
      case Success(conversation) => Ok(Json.toJson(conversation))
      case Failure(ex) => BadRequest(ex.toString)
    }
  }

  def postMessage(conversationId: Int) = Action.async(parse.json) { implicit request =>
      request.body.validate[MessagePostForm].fold(
        valid = postForm => {
          conversationService.addMessage(postForm.senderId, conversationId, postForm.message, postForm.recipient_id).map{
            case Success(message) => Ok(Json.toJson("Message "+message+" was added"))
            case Failure(ex) => BadRequest(ex.toString)
          }
        },
        invalid = error => Future.successful{ BadRequest("invalid json")}
      )
  }
}