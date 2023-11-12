package christmas

import OrderProcessing
import camp.nextstep.edu.missionutils.Console
import days

class ChristmasPromotion {
    private val orderProcessing = OrderProcessing()

    fun start() {
        println(MessageConstants.WELCOME_MESSAGE)

        val date = orderProcessing.getValidDate()

        println(MessageConstants.INPUT_MENUS)

        val inputMenu = Console.readLine()
        val orderedItems = orderProcessing.getOrderDetails(inputMenu)

        orderProcessing.checkOrderValidity(orderedItems)

        val currentDate = date.toInt()
        val totalAmount = orderProcessing.calculateTotalAmount(orderedItems)
        val discountedTotalAmount = orderProcessing.applyDiscounts(currentDate, totalAmount, days, orderedItems)

        orderProcessing.printOrderSummary(date, orderedItems, totalAmount, discountedTotalAmount)
    }
}