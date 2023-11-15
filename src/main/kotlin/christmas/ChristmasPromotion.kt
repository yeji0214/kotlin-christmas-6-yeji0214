package christmas

import camp.nextstep.edu.missionutils.Console

class ChristmasPromotion {
    private val orderProcessing = OrderProcessing()
    private val menuPriceCalculator = MenuPriceCalculator()
    private val discountCalculator = DiscountCalculator()
    private val inputView = InputView()
    private val outputView = OutputView()

    fun start() {
        outputView.printWelcomeMessage()

        val date = orderProcessing.getValidDate()
        val orderedItems = processOrder()
        if (orderedItems.isNotEmpty()) {
            val currentDate = date.toInt()
            val totalAmount = menuPriceCalculator.calculateTotalAmount(orderedItems)
            val discountedTotalAmount =
                    discountCalculator.applyDiscounts(currentDate, totalAmount, MenuData.days, orderedItems)

            orderProcessing.printOrderSummary(date, orderedItems, totalAmount, discountedTotalAmount)
        }

        Console.close()
    }

    private fun processOrder(): Map<String, Int> {
        var validOrder = false
        var orderedItems: Map<String, Int> = emptyMap()

        while (!validOrder) {
            val inputMenu = inputView.readMenu()
            orderedItems = orderProcessing.getOrderDetails(inputMenu)
            validOrder = orderProcessing.checkOrderValidity(orderedItems)
            try {
                if (!validOrder)
                    throw IllegalArgumentException(MessageConstants.ERROR_INVALID_ORDER)
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }
        }
        return orderedItems
    }
}