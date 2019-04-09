package commands

import atto._
import Atto._
import cats.implicits._
import commands.model.KSplitCommand

object KCommandParser {

  implicit def stringToParser(s: String): Parser[String] = string(s)

  val word: Parser[String] = takeWhile(!_.isWhitespace)

  val oneWord: Parser[String] = takeWhile1(!_.isWhitespace)

  val kstr: Parser[String] = stringOf(anyChar)

  val commandSplitter: Parser[KSplitCommand] = for {
    _ <- char('.')
    name <- oneWord
    rest <- opt(skipWhitespace *> kstr)
  } yield KSplitCommand(name, rest.getOrElse(""))

  def arg[T](s: Parser[T]): Parser[T] = opt(skipWhitespace) *> s
}
