package controllers

import play.api._
import play.api.mvc._
import services.{ConversationService, ConversationServiceDefault}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import fail._
import play.api.data._
import play.api.data.Forms._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scalaz.{-\/, \/-}


/**
  * I can make more specific error messages bu thats not the point, so i hope these ones will suffice
  */
class Application(conversationService: ConversationService) extends Controller {

  case class MessagePost(recipient_id: Int, senderId: Int, text: String)

  val MessagePostForm = Form(
    mapping(
      "sender_id" -> number,
      "recipient_id" -> number,
      "text" -> nonEmptyText
    )(MessagePost.apply)(MessagePost.unapply)
  )

  def getMessages(userId: Int, conversationId: Int) = Action.async { implicit request =>
    conversationService.fetchConversation(userId, conversationId).map{
      case \/-(conversation) => Ok(Json.toJson(conversation))
      case -\/(error: RepositoryError.DatabaseError) => InternalServerError("Error in the database")
      case -\/(error: RepositoryError.QueryError) => BadRequest("Wrong data")
      case -\/(error: RepositoryError.NoResults) => NotFound("Cant find the data in the database")
    }
  }

  def postMessage(conversationId: Int) = Action.async(parse.anyContent) { implicit request =>
      MessagePostForm.bindFromRequest().fold(
        formWithErrors => Future.successful{ BadRequest(formWithErrors.toString)},
        postForm => {
          conversationService.addMessage(postForm.senderId, conversationId, postForm.text, postForm.recipient_id).map{
            case \/-(message) => Ok(Json.toJson(message))
            case -\/(error: RepositoryError.DatabaseError) => InternalServerError("Error in the database")
            case -\/(error: RepositoryError.QueryError) => BadRequest("Wrong data")
            case -\/(error: RepositoryError.NoResults) => NotFound("Cant find the data in the database")
          }
        }
      )
  }
}