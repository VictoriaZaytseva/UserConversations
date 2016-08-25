package services

import com.github.mauricio.async.db.Connection
import models.{Conversation, Message}
import repositories.{ConversationRepository, MessageRepository, UserRepository}
import services.Service
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
/**
  * Created by victoria on 21/08/16.
  */
class ConversationServiceDefault(val db: DB,
                                 val userRepository: UserRepository,
                                 val messageRepository: MessageRepository,
                                 val conversationRepository: ConversationRepository) extends Service{

  def fetchConversation(userId: Int, conversationId: Int): Future[Try[Conversation]] ={
    transactional { implicit conn: Connection =>
      val fConversation = for {
        messages <- convFuture(messageRepository.getByConversationId(userId, conversationId))
        conversationWithoutMessages <- convFuture(conversationRepository.findById(userId, conversationId))
        conversation = Try(conversationWithoutMessages.copy(messages = messages))
      } yield conversation
      fConversation
    }
  }

  def addMessage(sender: Int, conversationId: Int, text: String, recepient: Int): Future[Try[Message]] = {
    transactional { implicit conn: Connection =>
      val fMessage = for {
        conversation <- conversationRepository.findById(sender, conversationId)
        message <- messageRepository.create(Message(text = text, sender = sender, recipient = recepient, conversationId = conversationId))
      } yield message
      fMessage
    }
  }
}
