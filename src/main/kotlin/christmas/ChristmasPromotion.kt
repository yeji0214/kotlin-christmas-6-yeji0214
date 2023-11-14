package christmas

import OrderProcessing
import camp.nextstep.edu.missionutils.Console
import days

class ChristmasPromotion {
    private val orderProcessing = OrderProcessing()
    private val menuPriceCalculator = MenuPriceCalculator()
    private val discountCalculator = DiscountCalculator()

    fun start() {
        println(MessageConstants.WELCOME_MESSAGE)

        val date = orderProcessing.getValidDate()

        println(MessageConstants.INPUT_MENUS)

        var validOrder = false
        var inputMenu = ""
        var orderedItems: Map<String, Int> = mutableMapOf()

        while (!validOrder) {
            inputMenu = Console.readLine()
            orderedItems = orderProcessing.getOrderDetails(inputMenu)

            validOrder = orderProcessing.checkOrderValidity(orderedItems)
        }


        val currentDate = date.toInt()
        val totalAmount = menuPriceCalculator.calculateTotalAmount(orderedItems)
        val discountedTotalAmount = discountCalculator.applyDiscounts(currentDate, totalAmount, days, orderedItems)

        orderProcessing.printOrderSummary(date, orderedItems, totalAmount, discountedTotalAmount)
    }
}