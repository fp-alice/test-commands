package commands

import org.scalatest.FunSuite

class KCommandManagerTest extends FunSuite {

  test("KCommandManager.getCommandForString")  {
    val existingCommand = KCommandManager.getCommandForString("nuker")
     assert(existingCommand.isDefined, "Command 'Nuker' wasn't found.")

    val nonExistentCommand = KCommandManager.getCommandForString("DoesntExist")
    assert(nonExistentCommand.isEmpty, "Non existent command was found.")
  }

  test("KCommandManager.parse") {
    val parse = KCommandManager.parseCommand(".help test")
    assert(parse.isRight, "'.help test' was not considered valid by the parser")

    val command = parse.right.get
    assert(command.name.equals("help"), "'command.name' was parsed incorrectly, expected 'help'")

    assert(command.rest.equals("test"), "'command.rest' was parsed incorrectly, expected 'test'")
  }

}
