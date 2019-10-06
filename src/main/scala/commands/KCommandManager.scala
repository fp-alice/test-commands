package commands

import atto.Atto._
import cats.effect.IO
import commands.impl.{KCommandHelp, KCommandNuker}
import commands.model.{KCommand, KSplitCommand}

object KCommandManager {

  type KCommandResult = Either[String, IO[Unit]]

  val commands: Seq[KCommand] = Seq(
    new KCommandNuker,
    new KCommandHelp
  )

  def interpret(s: String): Unit = {
    val res = parseCommand(s)
    res match {
      case Left(_) => println("Input was not a valid command string")
      case Right(ksc: KSplitCommand) => handleSplitCommand(ksc)
    }
  }

  def parseCommand(s: String): Either[String, KSplitCommand] = KCommandParser.commandSplitter.parseOnly(s).done.either

  def handleSplitCommand(ksc: KSplitCommand): Unit = {
    getCommandForString(ksc.name) match {
      case Some(command) => tryRunCommand(command, ksc.rest)
      case None => println("No such command exists")
    }
  }

  def getCommandForString(s: String): Option[KCommand] = {
    commands.find(_.names.contains(s))
  }

  def tryRunCommand(command: KCommand, args: String): Unit = {
    val results: Seq[KCommandResult] = command.subCommands.map(s => s(args))
    if (didMatchCommand(results)) {
      getFirstMatch(results) match {
        case Right(io) => io.unsafeRunSync()
        case Left(fail) => println(s"Failed with: $fail")
      }
    } else {
      println("Failed to match any subcommands")
    }
  }

  def didMatchCommand(x: Seq[KCommandResult]): Boolean = {
    x.exists(isMatch)
  }

  def getFirstMatch(x: Seq[KCommandResult]): KCommandResult = {
    x.find(isMatch).orNull
  }

  def isMatch(kCommandResult: KCommandResult): Boolean = {
    kCommandResult match {
      case Left(_) => false
      case Right(_) => true
    }
  }
}
