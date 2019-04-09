package commands.model

abstract class KCommand(val names: Seq[String]) {
  val subCommands: Seq[KCommandMatcher[_]]
}
