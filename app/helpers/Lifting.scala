package helpers

import scalaz.{-\/, \/, \/-, _}
import scala.concurrent.{ExecutionContext, Future}

/**
  * This trait provides several utility functions that can be used with Futures in
  * conjunction with scalaz.\/ and its scalaz.EitherT monad transformer.
  *
  * @tparam A is the failure type to use on the left-hand side of disjunctions.
  *           NB: the left-hand type is invariant in the EitherT transformer.
  */
trait Lifting[A] extends FutureMonad {

  /**
    * An implicit conversion to automatically call ".run" on EitherT monads when
    * it can be inferred that you want to unwrap the transformer.
    *
    * @param eithert the EitherT monad transformer to unwrap/run
    * @tparam B the right-hand type of the disjunction
    * @return an unwrapped Future disjunction
    */
  implicit def eitherRunner[B](eithert: EitherT[Future, A, B]): Future[\/[A, B]] = eithert.run

  /**
    * Lift a wrapped future disjunction into an EitherT.
    *
    * @tparam B
    * @return
    */
  def lift[B] = EitherT.eitherT[Future, A, B] _

}
