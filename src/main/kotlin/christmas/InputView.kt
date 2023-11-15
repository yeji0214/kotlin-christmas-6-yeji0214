package christmas
import camp.nextstep.edu.missionutils.Console

class InputView {
    fun readDate(): String {
        val input = Console.readLine()

        return input
    }

    fun readMenu(): String {
        println(MessageConstants.INPUT_MENUS)

        val inputMenu = Console.readLine()

        return inputMenu
    }
}