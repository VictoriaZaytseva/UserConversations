package repositories

import play.api.db._
import play.api.mvc._
import com.github.mauricio.async.db.exceptions.ConnectionStillRunningQueryException
import com.github.mauricio.async.db.postgresql.exceptions.GenericDatabaseException
import com.github.mauricio.async.db.{Connection, ResultSet, RowData}

import scala.concurrent.Future
import scala.util.Try
/**
  * Created by victoria on 21/08/16.
  */
trait Repository[A] {
  //val db: Database
  def constructor(row: RowData): A

  protected def queryList(queryText: String, parameters: Seq[Any] = Seq.empty[Any]) // format: OFF
                         (implicit conn: Connection): Future[Try[IndexedSeq[A]]] = { // format: ON
    val fRes = if (parameters.nonEmpty) {
      conn.sendPreparedStatement(queryText, parameters)
    } else {
      conn.sendQuery(queryText)
    }
    fRes.map {
      res => buildEntityList(res.rows, constructor)
    }.recover {
      case exception => throw exception
    }
  }

  def queryOne(queryText: String, parameters: Seq[Any] = Seq.empty[Any])(implicit conn: Connection): Future[Try[A]]= {
    val fRes = if (parameters.nonEmpty) {
      conn.sendPreparedStatement(queryText, parameters)
    }
    else {
      conn.sendQuery(queryText)
    }
    fRes.map {
      res => buildEntity(res.rows, constructor)
    }.recover {
      case exception: ConnectionStillRunningQueryException => throw exception
    }
  }

  protected def buildEntity[B](maybeResultSet: Option[ResultSet], build: RowData => B): Try[B] = {
    maybeResultSet match {
      case Some(resultSet) => resultSet.headOption match {
        case Some(firstRow) => Try(build(firstRow))
        case None => throw new Exception
      }
        case None =>  throw new Exception
      }
    }

  protected def buildEntityList[B](maybeResultSet: Option[ResultSet], build: RowData => B): Try[IndexedSeq[B]] = {
    maybeResultSet match {
      case Some(resultSet) =>Try(resultSet.map(build))
      case None => throw new Exception
    }
  }

}