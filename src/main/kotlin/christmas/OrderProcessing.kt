import camp.nextstep.edu.missionutils.Console
import christmas.DrinksOnlyException
import christmas.InputValidation
import christmas.MaximumMenusExceededException
import christmas.MessageConstants
import java.text.NumberFormat
import java.util.*

class OrderProcessing {
    private val inputValidation = InputValidation()
    fun calculateTotalAmount(orderedItems: Map<String, Int>): Int {
        var totalAmount = 0
        for ((menuName, quantity) in orderedItems) {
            val price = when {
                appetizer.containsKey(menuName) -> appetizer.getValue(menuName)
                main.containsKey(menuName) -> main.getValue(menuName)
                dessert.containsKey(menuName) -> dessert.getValue(menuName)
                drink.containsKey(menuName) -> drink.getValue(menuName)
                else -> 0
            }
            totalAmount += price * quantity
        }
        return totalAmount
    }

    fun applyDiscounts(currentDate: Int, totalAmount: Int, days: List<String>, orderedItems: Map<String, Int>): Int {
        val isWeekend = (currentDate % 7 == days.indexOf(MessageConstants.FRI) || currentDate % 7 == days.indexOf(MessageConstants.SAT))
        val additionalDiscount = calculateAdditionalDiscount(isWeekend, orderedItems)
        val christmasDayDiscount = calculateChristmasDayDiscount(currentDate)
        if (christmasDayDiscount > 0)
            benefitsDetails[MessageConstants.CHRISTMAS_DDAY_SALE] = christmasDayDiscount
        val specialDiscount = calculateSpecialDiscount(currentDate)
        if (specialDiscount > 0)
            benefitsDetails[MessageConstants.SPECIAL_SALE] = specialDiscount
        val totalDiscount = calculateTotalDiscount(totalAmount, christmasDayDiscount, additionalDiscount, specialDiscount)

        return totalAmount - totalDiscount
    }

    fun calculateAdditionalDiscount(isWeekend: Boolean, orderedItems: Map<String, Int>): Int {
        var additionalDiscount = 0

        if (isWeekend) {
            val mainMenusCount = orderedItems.filter { main.containsKey(it.key) }.values.sum()
            additionalDiscount = 2023 * mainMenusCount
            if (additionalDiscount > 0) {
                benefitsDetails[MessageConstants.WEEKEND_SALE] = additionalDiscount
            }
        } else {
            val dessertMenusCount = orderedItems.filter { dessert.containsKey(it.key) }.values.sum()
            additionalDiscount = 2023 * dessertMenusCount
            if (additionalDiscount > 0) {
                benefitsDetails[MessageConstants.WEEKDAY_SALE] = additionalDiscount
            }
        }

        return additionalDiscount
    }

    fun calculateChristmasDayDiscount(currentDate: Int): Int {
        return if (currentDate in 1..25) {
            1000 + (currentDate - 1) * 100
        } else {
            0
        }
    }

    fun calculateSpecialDiscount(currentDate: Int): Int {
        return if (currentDate % 7 == 3 || currentDate == 25) {
            1000
        } else {
            0
        }
    }

    fun calculateTotalDiscount(totalAmount: Int, christmasDayDiscount: Int, additionalDiscount: Int, specialDiscount: Int): Int {
        return if (totalAmount > 10000) {
            christmasDayDiscount + additionalDiscount + specialDiscount
        } else {
            0
        }
    }

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

    fun checkOrderValidity(orderedItems: Map<String, Int>) {
        if (orderedItems.all { drink.containsKey(it.key) } && orderedItems.isNotEmpty()) {
            throw DrinksOnlyException(MessageConstants.ERROR_JUST_ORDER_DRINK)
        }

        if (orderedItems.values.sum() > 20) {
            throw MaximumMenusExceededException(MessageConstants.ERROR_MORE_THAN_20_MENUS)
        }
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
