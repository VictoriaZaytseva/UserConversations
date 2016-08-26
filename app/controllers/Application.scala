package controllers

import play.api._
import play.api.mvc._
import services.ConversationServiceDefault
import play.api.libs.json._
import play.api.libs.functional.syntax._
import fail._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scalaz.{-\/, \/-}

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
      case \/-(conversation) => Ok(Json.toJson(conversation))
      case -\/(error: RepositoryError.DatabaseError) => InternalServerError("Error in the database")
      case -\/(error: RepositoryError.QueryError) => BadRequest("Wrong data")
      case -\/(error: RepositoryError.NoResults) => NotFound("Cant find the data in the database")
    }
  }

  def postMessage(conversationId: Int) = Action.async(parse.json) { implicit request =>
      request.body.validate[MessagePostForm].fold(
        valid = postForm => {
          conversationService.addMessage(postForm.senderId, conversationId, postForm.message, postForm.recipient_id).map{
            case \/-(message) => Ok(Json.toJson(message))
            case -\/(error: RepositoryError.DatabaseError) => InternalServerError("Error in the database")
            case -\/(error: RepositoryError.QueryError) => BadRequest("Wrong data")
            case -\/(error: RepositoryError.NoResults) => NotFound("Cant find the data in the database")
          }
        },
        invalid = error => Future.successful{ BadRequest("invalid json")}
      )
  }

  def index = Action {
    Ok(views.html.index())
  }
}