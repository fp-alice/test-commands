import commands.KCommandManager

object Main {

  def repl(): Unit = {
    print("> ")
    val input: String = scala.io.StdIn.readLine()
    if (input == "exit") {
      println("Exiting...")
    } else {
      KCommandManager.interpret(input)
      repl()
    }
  }

  def main(args: Array[String]): Unit = {
    repl()
  }

}
