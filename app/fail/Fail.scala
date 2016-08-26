
package fail

sealed trait Fail

final case class DatabaseError(message: String, exception: Option[Throwable] = None) extends Fail
final case class ThrowableFail(e: Throwable) extends Fail

