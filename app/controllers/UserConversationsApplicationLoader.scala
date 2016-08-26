package controllers

import _root_.play.api.ApplicationLoader.Context
import _root_.play.api.{ApplicationLoader, BuiltInComponents, BuiltInComponentsFromContext, Logger}
import _root_.play.api.inject.{Injector, NewInstanceInjector, SimpleInjector}
import play.api.{BuiltInComponents, Play}
import services.{ConversationServiceDefault, DB, PostgresDB}
import _root_.play.api.routing.Router
import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.pool.PoolConfiguration
import repositories.impl.{ConversationRepositorySql, MessageRepositorySql, UserRepositorySql}
import play.api.{ApplicationLoader, BuiltInComponentsFromContext}
import router.Routes

class UserConversations(context: Context)  extends BuiltInComponentsFromContext(context){
 val dbConfig: Configuration = new Configuration(
   username = configuration.getString("db.postgresql.username").getOrElse("krispii_user"),
   host = configuration.getString("db.postgresql.host").getOrElse("localhost"),
   password = configuration.getString("db.postgresql.password"),
   database = configuration.getString("db.postgresql.database"),
   port = configuration.getInt("db.postgresql.port").getOrElse(5432) // scalastyle:ignore
 )
 val poolConfig: PoolConfiguration = new PoolConfiguration(
   maxObjects = configuration.getInt("db.postgresql.maxConnections").getOrElse(4), // scalastyle:ignore
   maxIdle = configuration.getLong("db.postgresql.maxIdle").getOrElse(1000L), // scalastyle:ignore
   maxQueueSize = configuration.getInt("db.postgresql.maxQueueSize").getOrElse(1000), // scalastyle:ignore
   validationInterval = configuration.getLong("db.postgresql.validationInterval").getOrElse(500L) // scalastyle:ignore
 )

  lazy val database: DB = new PostgresDB(dbConfig, poolConfig)

  lazy val userRepository = new UserRepositorySql()
  lazy val conversationRepository = new ConversationRepositorySql()
  lazy val messageRepository = new MessageRepositorySql()
  lazy val conversationServiceDefault = new ConversationServiceDefault(database, userRepository, messageRepository, conversationRepository)
  lazy val applicationController = new controllers.Application(conversationServiceDefault)
  lazy val assets = new controllers.Assets(httpErrorHandler)

  override def router: Router = new Routes(httpErrorHandler, assets, applicationController) withPrefix "/"
}

class UserConversationsApplicationLoader extends ApplicationLoader {
  def load(context: Context) = {
    Logger.debug("Loading application...")
    new UserConversations(context).application
  }
}