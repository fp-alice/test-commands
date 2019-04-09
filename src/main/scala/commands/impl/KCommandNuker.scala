package commands.impl

import atto.Atto._
import cats.effect.IO
import cats.implicits._
import cats.instances.boolean
import commands.KCommandParser._
import commands.model.{KCommand, KCommandMatcher}

import scala.language.implicitConversions
import scala.util.Try

class KCommandNuker extends KCommand(Seq("nuker", "n")) {

  override val subCommands: Seq[KCommandMatcher[_]] = Seq(
    KCommandMatcher[(String, Option[String])](
      arg("add") *> arg(word) ~ opt(arg(oneWord)),
      "Add targets to nuker",
      Seq(".nuker add <block>", ".nuker add any <target string>"),
      addToNuker
    ),
    new KCommandMatcher[String](
      arg("load") *> arg(oneWord),
      "Loads a preset list of nuker targets",
      Seq(".nuker load <preset>"),
      loadPreset
    ),
    new KCommandMatcher[(String, Option[String])](
      arg("list") *> arg(word) ~ opt(arg(oneWord)),
      "List blocks, presets, and blocks in a preset",
      Seq(".nuker list blocks", ".nuker list presets", ".nuker list preset <preset>"),
      nukerList
    ),
    new KCommandMatcher[Int](
      arg("speed") *> arg(int),
      "Set nuker speed",
      Seq(".nuker speed 0"),
      setSpeed
    ),
    new KCommandMatcher[Double](
      arg("range") *> arg(double),
      "Set nuker range",
      Seq(".nuker range 4.5"),
      setRange
    ),
    new KCommandMatcher[String](
      arg("above") *> arg(oneWord),
      "Can nuker break blocks above you",
      Seq(".nuker above false"),
      setAbove
    ),
    new KCommandMatcher[String](
      arg("clear"),
      "Clear nuker targets",
      Seq(".nuker clear"),
      _ => clearBlocks
    )
  )

  def clearBlocks: IO[Unit] = IO {
    println("Clear blocks")
  }

  def setAbove(string: String): IO[Unit] = IO {
    val bool: Boolean = Try(string.toBoolean).getOrElse(false)
    println(s"set bool $bool")
  }

  def setRange(range: Double): IO[Unit] = IO {
    println(s"Set nuker range to $range")
  }

  def setSpeed(speed: Int): IO[Unit] = IO {
    println(s"Set nuker speed to $speed")
  }

  def nukerList(args: (String, Option[String])): IO[Unit] = IO {
    args match {
      case ("blocks", _) => println("[List blocks here]")
      case ("presets", _) => println("[List presets here]")
      case ("preset", Some(preset)) if preset.nonEmpty => println(s"[List preset $preset here]")
      case _ => println("Bad arguments")
    }
  }

  def addToNuker(args: (String, Option[String])): IO[Unit] = IO {
    args match {
      case ("any", Some(target)) if target.nonEmpty => println(s"Adding any $target to nuker")
      case (target, None) if target.nonEmpty => println(s"Adding block $target to nuker")
      case _ => println("Bad arguments")
    }
  }

  def loadPreset(preset: String): IO[Unit] = IO {
    println(s"Loaded preset $preset")
  }
}


/**
  * ESP Xray example for pants
  * new KCommandMatcher[(String, String)](
  * string("add") ~> string("block") ~> takeWhile(!_.isWhitespace) ~ takeWhile(!_.isWhitespace),
  * "Adds a block with a specified name and hex color",
  * (args: (String, String)) => IO { println(s"Add block $args._1 with color $args._2") }
  * )
  */
