package christmas
import camp.nextstep.edu.missionutils.Console

class InputView {
    private val outputView = OutputView()

    fun readDate(): String {
        val input = Console.readLine()

        return input
    }

    fun readMenu(): String {
        outputView.printInputMenus()
        val inputMenu = Console.readLine()

        return inputMenu
    }
}