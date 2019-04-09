package commands.impl

import cats.effect.IO
import commands.KCommandManager
import commands.model.{KCommand, KCommandMatcher}
import commands.KCommandParser._
import atto._
import Atto._

class KCommandHelp extends KCommand(Seq("help", "h")) {
  override val subCommands: Seq[KCommandMatcher[_]] = Seq (
    new KCommandMatcher[String](
      arg(oneWord),
      "Gets help info for a command",
      Seq(".help <command>"),
      getHelpForCommands
    ),
    new KCommandMatcher[String](
      arg(""),
      ".help",
      Seq(".help"),
      _ => printHelp()
    )
  )

  def printHelp(): IO[Unit] = IO {
    println("KitKat commands")
    KCommandManager.commands.foreach(kc => {
      println(s" - ${kc.names.mkString(", ")}")
    })
    println("Run `.help <command> for more information about a specific command")
  }

  def printHelpForCommand(cmd: KCommand): Unit = {
    cmd.subCommands.foreach(sub => {
      println(sub.docstring)
      sub.usage.foreach(u => println(s" - $u"))
    })
  }

  def getHelpForCommands(command: String): IO[Unit] = IO {
    KCommandManager.commands.find(_.names.contains(command)) match {
      case Some(cmd: KCommand) => printHelpForCommand(cmd)
      case None =>
    }
  }
}
