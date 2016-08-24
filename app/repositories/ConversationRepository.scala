package repositories

import models.User

import scala.concurrent.Future
import scala.util.Try

/**
  * Created by victoria on 21/08/16.
  */
trait ConversationRepository {
  def findById(userId: Int, conversationId: String): Future[Try[User]]
  def update(id: Int): Future[Try[User]]

}
