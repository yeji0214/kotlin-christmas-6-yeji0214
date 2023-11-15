package christmas
import camp.nextstep.edu.missionutils.Console
import java.text.NumberFormat
import java.util.*

class OrderProcessing {
    private val inputValidation = InputValidation()
    private var totalBenefitAmount = 0

    fun getValidDate(): String {
        var validDate = false
        var date: String = ""

        do {
            try {
                date = Console.readLine()

                if (!inputValidation.isValidDate(date)) {
                    throw IllegalArgumentException(MessageConstants.ERROR_INVALID_DATE)
                } else {
                    validDate = true
                }
            } catch (e: IllegalArgumentException) {
                println(e.message)
            }

        } while (!validDate)

        return date
    }

    fun getOrderDetails(inputMenu: String): Map<String, Int> {
        val menuItems = inputMenu.split(",")
        val orderedItems = mutableMapOf<String, Int>()

        for (menuItem in menuItems) {
            val (menuName, quantity) = inputValidation.receiveAndVerifyMenuAndQuantity(menuItem)
            orderedItems[menuName] = quantity
        }

        return orderedItems
    }

    fun checkOrderValidity(orderedItems: Map<String, Int>): Boolean {
        if (orderedItems.all { MenuData.drink.containsKey(it.key) } && orderedItems.isNotEmpty()) {
            //println(MessageConstants.ERROR_INVALID_ORDER)
            return false
        }

        if (orderedItems.values.sum() > 20) {
            //println(MessageConstants.ERROR_INVALID_ORDER)
            return false
        }

        return true
    }

    fun determineBadge(totalBenefitAmount: Int): String {
        return when {
            totalBenefitAmount >= 20000 -> MessageConstants.SANTA
            totalBenefitAmount >= 10000 -> MessageConstants.TREE
            totalBenefitAmount >= 5000 -> MessageConstants.STAR
            else -> ""
        }
    }

    fun printOrderSummary(date: String, orderedItems: Map<String, Int>, totalAmount: Int, discountedTotalAmount: Int) {
        printEventInfo(date)
        printOrderedItems(orderedItems)
        printTotalAmount(totalAmount)
        printGiftMenu(totalAmount)
        printBenefitsDetails()
        printTotalBenefitAmount()
        printEstimatedPaymentAmount(discountedTotalAmount)
        printDecemberEventBadge(totalBenefitAmount)
    }

    private fun printEventInfo(date: String) {
        val formattedDate = "${MessageConstants.EVENT_MESSAGE_START}${date}${MessageConstants.EVENT_MESSAGE_END}"
        println(formattedDate)
    }

    private fun printOrderedItems(orderedItems: Map<String, Int>) {
        println(MessageConstants.ORDER_MENU)
        for ((menuName, quantity) in orderedItems) {
            println("${menuName} ${quantity}${MessageConstants.UNIT}")
        }
    }

    private fun printTotalAmount(totalAmount: Int) {
        val numberFormat = NumberFormat.getNumberInstance(Locale("en"))
        val formattedTotalAmount = numberFormat.format(totalAmount)
        println("\n${MessageConstants.TOTAL_ORDER_AMOUNT_BEFORE_DISCOUNT}\n${formattedTotalAmount}${MessageConstants.WON}")
    }

    private fun printGiftMenu(totalAmount: Int) {
        val giftMenu = if (totalAmount >= 120000) {
            MessageConstants.ONE_CHAMPAGNE
        } else {
            MessageConstants.NONE
        }

        if (giftMenu == MessageConstants.ONE_CHAMPAGNE) {
            MenuData.benefitsDetails[MessageConstants.GIFT_EVENT] = 25000
        }
        println("\n${MessageConstants.GIFT_MENU}\n$giftMenu")
    }

    private fun printBenefitsDetails() {
        totalBenefitAmount = 0
        println("\n${MessageConstants.BENEFITS_DETAILS}")
        if (MenuData.benefitsDetails.isEmpty()) {
            println(MessageConstants.NONE)
        } else {
            for ((benefitName, benefitAmount) in MenuData.benefitsDetails) {
                val formattedBenefitAmount = NumberFormat.getNumberInstance(Locale("en")).format(benefitAmount)
                totalBenefitAmount += benefitAmount
                println("${benefitName}: -${formattedBenefitAmount}${MessageConstants.WON}")
            }
        }
    }

    private fun printTotalBenefitAmount() {
        println("\n${MessageConstants.TOTAL_BENEFIT_AMOUNT}")
        val formattedTotalBenefitAmount = NumberFormat.getNumberInstance(Locale("en")).format(totalBenefitAmount)
        if (totalBenefitAmount == 0)
            println(MessageConstants.ZERO_WON)
        else
            println("-${formattedTotalBenefitAmount}${MessageConstants.WON}")
    }

    private fun printEstimatedPaymentAmount(discountedTotalAmount: Int) {
        val numberFormat = NumberFormat.getNumberInstance(Locale("en"))
        val formattedDiscountedTotalAmount = numberFormat.format(discountedTotalAmount)
        println("\n${MessageConstants.ESTIMATED_PAYMENT_AMOUNT_AFTER_DISCOUNT}\n${formattedDiscountedTotalAmount}${MessageConstants.WON}")
    }

    private fun printDecemberEventBadge(totalBenefitAmount: Int) {
        println("\n${MessageConstants.DECEMBER_EVENT_BADGE}")
        val badge = determineBadge(totalBenefitAmount)
        if (badge.isEmpty())
            println(MessageConstants.NONE)
        else
            println(badge)
    }
}
