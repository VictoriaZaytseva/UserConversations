package fail

/**
  * Custom errors for pattern matching
  */
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
// repository errors. with ErrorInion we can have different error groups in the future
object RepositoryError
  extends DatabaseErrorT
    with NoResultsT
    with QueryErrorT
