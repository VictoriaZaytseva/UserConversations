package helpers

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.concurrent.ExecutionContext.Implicits.global

trait Converting {

  def convFuture[T](ft: Future[Try[T]]): Future[T] =
    ft.flatMap {
      _ match {
        case Success(s) => Future.successful(s)
        case Failure(f) => Future.failed(f)
      }
    }
}