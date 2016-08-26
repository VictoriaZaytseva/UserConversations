package services

/**
  * Created by victoria on 21/08/16.
  */
import scala.concurrent.ExecutionContext.Implicits.global
import com.github.mauricio.async.db.Connection
import fail.{ErrorUnion, RepositoryError}
import helpers.{Lifting}

import scala.concurrent.Future
import services.DB

trait Service extends Lifting[ErrorUnion#Fail]{
  /**
    * Database connection (pool). Services will take connections from
    * this pool when making repository calls.
    */
  val db: DB

  /**
    * Takes a function that returns a future, and runs it inside a database
    * transaction.
    */
  def transactional[A](f: Connection => Future[A]): Future[A] = {
    db.pool.inTransaction { conn =>
      conn.sendQuery("SET TRANSACTION ISOLATION LEVEL REPEATABLE READ").flatMap { _ =>
        f(conn)
      }
    }.recover {
      case exception => throw exception
    }
  }

}