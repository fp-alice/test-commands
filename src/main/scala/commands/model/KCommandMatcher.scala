package commands.model

import atto._
import Atto._
import cats.effect.IO
import cats.implicits._

case class KCommandMatcher[T](parser: Parser[T],
                              docstring: String,
                              usage: Seq[String],
                              command: T => IO[Unit]) {
  def apply(s: String): Either[String, IO[Unit]] = parser.parseOnly(s).done.either.map(t => command(t))
}
