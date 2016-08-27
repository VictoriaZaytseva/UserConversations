package services

import com.github.mauricio.async.db.Configuration
import com.github.mauricio.async.db.Connection
import com.github.mauricio.async.db.postgresql.pool.PostgreSQLConnectionFactory
import com.github.mauricio.async.db.pool.{ConnectionPool, PoolConfiguration}
import com.typesafe.config.ConfigFactory

trait DB {
  val dbconfig: Configuration
  def pool: Connection
}

class PostgresDB(val dbconfig: Configuration, val poolConfig: PoolConfiguration) extends DB {
  val config = ConfigFactory.load()
  //val cacheExpiry = Option(config.getInt("app.cache.expires")).getOrElse(5)
  lazy val factory = new PostgreSQLConnectionFactory(dbconfig)
  override def pool: Connection = connectionPool

  private val connectionPool = new ConnectionPool(factory, poolConfig)
}