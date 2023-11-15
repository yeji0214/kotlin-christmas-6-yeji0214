package christmas

import benefitsDetails
import dessert
import mainMenu


class DiscountCalculator {
    fun applyDiscounts(currentDate: Int, totalAmount: Int, days: List<String>, orderedItems: Map<String, Int>): Int {
        val isWeekend = (currentDate % 7 == days.indexOf(MessageConstants.FRI) || currentDate % 7 == days.indexOf(MessageConstants.SAT))
        val weekdayOrWeekendDiscount = calculateWeekdayOrWeekendDiscount(isWeekend, orderedItems)
        val christmasDayDiscount = calculateChristmasDayDiscount(currentDate)
        if (christmasDayDiscount > 0)
            benefitsDetails[MessageConstants.CHRISTMAS_DDAY_SALE] = christmasDayDiscount
        val specialDiscount = calculateSpecialDiscount(currentDate)
        if (specialDiscount > 0)
            benefitsDetails[MessageConstants.SPECIAL_SALE] = specialDiscount
        val totalDiscount = calculateTotalDiscount(totalAmount, christmasDayDiscount, weekdayOrWeekendDiscount, specialDiscount)

        return totalAmount - totalDiscount
    }

    fun calculateWeekdayOrWeekendDiscount(isWeekend: Boolean, orderedItems: Map<String, Int>): Int {
        var additionalDiscount = 0

        if (isWeekend) {
            val mainMenusCount = orderedItems.filter { mainMenu.containsKey(it.key) }.values.sum()
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

    fun calculateTotalDiscount(totalAmount: Int, christmasDayDiscount: Int, weekdayOrWeekendDiscount: Int, specialDiscount: Int): Int {
        return if (totalAmount > 10000) {
            christmasDayDiscount + weekdayOrWeekendDiscount + specialDiscount
        } else {
            0
        }
    }
}