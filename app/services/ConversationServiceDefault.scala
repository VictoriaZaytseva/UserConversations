package services

import com.github.mauricio.async.db.Connection
import fail.{ErrorUnion, RepositoryError}
import models.{Conversation, Message}
import repositories.{ConversationRepository, MessageRepository, UserRepository}
import services.Service

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scalaz.\/
/**
  * Created by victoria on 21/08/16.
  */
class ConversationServiceDefault(val db: DB,
                                 val userRepository: UserRepository,
                                 val messageRepository: MessageRepository,
                                 val conversationRepository: ConversationRepository) extends Service{

  def fetchConversation(userId: Int, conversationId: Int): Future[\/[ErrorUnion#Fail, Conversation]] ={
    transactional { implicit conn: Connection =>
      val fConversation = for {
        conversationWithoutMessages <- lift(conversationRepository.findById(conversationId))
        messages <- lift(messageRepository.getByConversationId(conversationId))
        conversation = conversationWithoutMessages.copy(messages = messages)
      } yield conversation
      fConversation.run
    }
  }

  def addMessage(sender: Int, conversationId: Int, text: String, recepient: Int): Future[\/[ErrorUnion#Fail, Message]] = {
    transactional { implicit conn: Connection =>
      val fMessage = for {
        conversation <- lift(conversationRepository.findById(conversationId))
        updatedConversation <- lift(conversationRepository.update(conversationId, conversation.messageCount+1))
        message <- lift(messageRepository.create(Message(text = text, sender = sender, recipient = recepient, conversationId = conversationId)))
      } yield message
      fMessage.run
    }
  }
}
