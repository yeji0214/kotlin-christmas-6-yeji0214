import camp.nextstep.edu.missionutils.Console
import christmas.MessageConstants
import java.text.NumberFormat
import java.util.*

fun main() {
    println(MessageConstants.WELCOME_MESSAGE)

    val date = getValidDate()

    println(MessageConstants.INPUT_MENUS)

    val inputMenu = Console.readLine()
    val orderedItems = getOrderDetails(inputMenu)

    checkOrderValidity(orderedItems)

    val currentDate = date.toInt()
    val totalAmount = calculateTotalAmount(orderedItems)
    val discountedTotalAmount = applyDiscounts(currentDate, totalAmount, days, orderedItems)

    printOrderSummary(date, orderedItems, totalAmount, discountedTotalAmount)
}
