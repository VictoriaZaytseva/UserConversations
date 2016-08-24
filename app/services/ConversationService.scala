package services

import com.github.mauricio.async.db.Connection
import models.{Conversation, Message}
import repositories.{MessageRepository, UserRepository}
import services.Service

import scala.util.Try
/**
  * Created by victoria on 21/08/16.
  */
class ConversationService(val db: DB, val userRepository: UserRepository, val messageRepository: MessageRepository) extends Service{
  def fetchConversation(userId: Int, conversationId: Int): Try[Conversation] ={
    transactional { implicit conn: Connection =>
      val fConversation = for {

      } yield conversation
    }
  }
  def addMessage(userId: Int, conversationId: Int, text: String): Try[Message] = {
    transactional { implicit conn: Connection =>
      val fMessage = for {

      } yield message
    }
  }
}
