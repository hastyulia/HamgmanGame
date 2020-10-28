package exercises
import util.control.Breaks._

// Используя функции io.readLine и io.printLine напишите игру "Виселица"
// Пример ввода и тест можно найти в файле src/test/scala/fintech/homework01/HangmanTest.scala
// Тест можно запустить через в IDE или через sbt (написав в консоли sbt test)

// Правила игры "Виселица"
// 1) Загадывается слово
// 2) Игрок угадывает букву
// 3) Если такая буква есть в слове - они открывается
// 4) Если нет - рисуется следующий элемент висельника
// 5) Последней рисуется "веревка". Это означает что игрок проиграл
// 6) Если игрок все еще жив - перейти к пункту 2

// Пример игры:

// Word: _____
// Guess a letter:
// a
// Word: __a_a
// Guess a letter:
// b
// +----
// |
// |
// |
// |
// |

// и т.д.

class Hangman(io: IODevice) {
  def play(word: String): Unit = {
    val letters = word.split("")
    val user_word = Array.fill(letters.length)("_")
    var user_correct_letters = 0
    var faults = 0
    io.printLine("Word: " + user_word.mkString(""))
    while (user_correct_letters != letters.length && faults != stages.size) {
      breakable {
        io.printLine("Guess a letter:")
        val user_letter = io.readLine()
        if (letters.indexOf(user_letter) < 0) {
          faults += 1
          io.printLine(stages(faults - 1))
          break
        }
        for (n <- user_word.indices) {
          if (letters(n) == user_letter) {
            user_word(n) = letters(n)
            user_correct_letters = user_correct_letters + 1
          }
        }
      }
      io.printLine("Word: " + user_word.mkString(""))
    }
    if (faults == stages.size)
      io.printLine("You are dead")
    else
      io.printLine("GG")
  }

  val stages = List(
    """+----
      ||
      ||
      ||
      ||
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||
      ||
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||   |
      ||
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||   |
      ||  /
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||   |
      ||  / \
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||  /|
      ||  / \
      ||
      |""".stripMargin,
    """+----
      ||
      ||   O
      ||  /|\
      ||  / \
      ||
      |""".stripMargin,
    """+----
      ||   |
      ||   O
      ||  /|\
      ||  / \
      ||
      |""".stripMargin
  )
}

trait IODevice {
  def printLine(text: String): Unit
  def readLine(): String
}
