import camp.nextstep.edu.missionutils.Console
import christmas.*
import java.text.NumberFormat
import java.util.*

class OrderProcessing {
    private val inputValidation = InputValidation()
    fun getValidDate(): String {
        var validDate = false
        var date: String

        do {
            date = Console.readLine()

            if (!inputValidation.isValidDate(date)) {
                println(MessageConstants.ERROR_INVALID_DATE)
            } else {
                validDate = true
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

    fun checkOrderValidity(orderedItems: Map<String, Int>) : Boolean {
        if (orderedItems.all { drink.containsKey(it.key) } && orderedItems.isNotEmpty()) {
            println(MessageConstants.ERROR_INVALID_ORDER)
            return false
        }

        if (orderedItems.values.sum() > 20) {
            println(MessageConstants.ERROR_INVALID_ORDER)
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
        val numberFormat = NumberFormat.getNumberInstance(Locale("en")) // Use Locale for comma formatting
        val formattedTotalAmount = numberFormat.format(totalAmount)
        val formattedDiscountedTotalAmount = numberFormat.format(discountedTotalAmount)

        println("${MessageConstants.EVENT_MESSAGE_START}${date}${MessageConstants.EVENT_MESSAGE_END}")
        println(MessageConstants.ORDER_MENU)
        for ((menuName, quantity) in orderedItems) {
            println("${menuName} ${quantity}${MessageConstants.UNIT}")
        }
        println("\n${MessageConstants.TOTAL_ORDER_AMOUNT_BEFORE_DISCOUNT}\n${formattedTotalAmount}${MessageConstants.WON}")

        val giftMenu = if (totalAmount >= 120000) {
            MessageConstants.ONE_CHAMPAGNE
        } else {
            MessageConstants.NONE
        }

        if (giftMenu == MessageConstants.ONE_CHAMPAGNE) {
            benefitsDetails[MessageConstants.GIFT_EVENT] = 25000
        }
        println("\n${MessageConstants.GIFT_MENU}\n$giftMenu")

        println("\n${MessageConstants.BENEFITS_DETAILS}")
        var totalBenefitAmount = 0
        if (benefitsDetails.isEmpty()) {
            println(MessageConstants.NONE)
        } else {
            for ((benefitName, benefitAmount) in benefitsDetails) {
                val formattedBenefitAmount = NumberFormat.getNumberInstance(Locale("en")).format(benefitAmount)
                totalBenefitAmount += benefitAmount
                println("${benefitName}: -${formattedBenefitAmount}${MessageConstants.WON}")
            }
        }

        println("\n${MessageConstants.TOTAL_BENEFIT_AMOUNT}")
        val formattedTotalBenefitAmount = NumberFormat.getNumberInstance(Locale("en")).format(totalBenefitAmount)
        if (totalBenefitAmount == 0)
            println(MessageConstants.ZERO_WON)
        else
            println("-${formattedTotalBenefitAmount}${MessageConstants.WON}")

        val badge = determineBadge(totalBenefitAmount)
        println("\n${MessageConstants.ESTIMATED_PAYMENT_AMOUNT_AFTER_DISCOUNT}\n${formattedDiscountedTotalAmount}${MessageConstants.WON}")

        println("\n${MessageConstants.DECEMBER_EVENT_BADGE}")
        if (badge.isEmpty())
            println(MessageConstants.NONE)
        else
            println(badge)
    }
}
