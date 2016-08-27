package services

import fail.ErrorUnion
import models.{Conversation, Message}

import scala.concurrent.Future
import scalaz.\/

trait ConversationService extends Service{
  def addMessage(sender: Int, conversationId: Int, text: String, recepient: Int): Future[\/[ErrorUnion#Fail, Message]]
  def fetchConversation(userId: Int, conversationId: Int): Future[\/[ErrorUnion#Fail, Conversation]]
}
