package fail

sealed trait ErrorUnion {
  sealed trait Fail
}

sealed trait DatabaseErrorT extends ErrorUnion {
  case class DatabaseError(message: String, exception: Option[Throwable] = None) extends Fail
}

sealed trait QueryErrorT extends ErrorUnion {
  case class QueryError(message: String, exception: Option[Throwable] = None) extends Fail
}

sealed trait NoResultsT extends ErrorUnion {
  case class NoResults(message: String) extends Fail
}
// other types of error may follow
object RepositoryError
  extends DatabaseErrorT
    with NoResultsT
    with QueryErrorT
