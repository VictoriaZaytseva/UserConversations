package repositories

import scala.util.control.NonFatal
import play.api.db._
import play.api.mvc._
import com.github.mauricio.async.db.exceptions.ConnectionStillRunningQueryException
import com.github.mauricio.async.db.postgresql.exceptions.GenericDatabaseException
import com.github.mauricio.async.db.{Connection, ResultSet, RowData}
import helpers.Lifting
import fail._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try
import scalaz.{-\/, \/, \/-}
/**
  * Created by victoria on 21/08/16.
  */
trait Repository[A] extends Lifting[RepositoryError.Fail]{

  def constructor(row: RowData): A

  protected def queryList(queryText: String, parameters: Seq[Any] = Seq.empty[Any]) // format: OFF
                         (implicit conn: Connection): Future[\/[RepositoryError.Fail, IndexedSeq[A]]] = { // format: ON
    val fRes = if (parameters.nonEmpty) {
      conn.sendPreparedStatement(queryText, parameters)
    } else {
      conn.sendQuery(queryText)
    }
    fRes.map {
      res => buildEntityList(res.rows, constructor)
    }.recover {
      case exception: ConnectionStillRunningQueryException =>
        -\/(RepositoryError.DatabaseError("Attempted to send concurrent queries in the same transaction.", Some(exception)))
      case exception: GenericDatabaseException =>
        val fields = exception.errorMessage.fields
        -\/(RepositoryError.QueryError("", Some(exception)))
      case exception => throw exception
    }
  }

  def queryOne(queryText: String, parameters: Seq[Any] = Seq.empty[Any])(implicit conn: Connection): Future[\/[RepositoryError.Fail, A]]= {
    val fRes = if (parameters.nonEmpty) {
      conn.sendPreparedStatement(queryText, parameters)
    }
    else {
      conn.sendQuery(queryText)
    }
    fRes.map {
      res => buildEntity(res.rows, constructor)
    }.recover {
      case exception: ConnectionStillRunningQueryException =>
        -\/(RepositoryError.DatabaseError("Attempted to send concurrent queries in the same transaction.", Some(exception)))
      case exception: GenericDatabaseException =>
        val fields = exception.errorMessage.fields
        -\/(RepositoryError.QueryError("", Some(exception)))
      case exception => throw exception
    }
  }

  protected def buildEntity[B](maybeResultSet: Option[ResultSet], build: RowData => B): \/[RepositoryError.Fail, B] = {
    maybeResultSet match {
      case Some(resultSet) => resultSet.headOption match {
        case Some(firstRow) => \/-(build(firstRow))
        case None => -\/(RepositoryError.NoResults("No data in the database"))
      }
        case None =>  -\/(RepositoryError.NoResults("No data in the database"))
      }
    }

  protected def buildEntityList[B](maybeResultSet: Option[ResultSet], build: RowData => B): \/[RepositoryError.Fail, IndexedSeq[B]] = {
    maybeResultSet match {
      case Some(resultSet) => \/-(resultSet.map(build))
      case None => -\/(RepositoryError.NoResults("No data in the database"))
    }
  }

}
