import camp.nextstep.edu.missionutils.Console
import java.text.NumberFormat
import java.util.*

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
    val isWeekend = (currentDate % 7 == days.indexOf("금") || currentDate % 7 == days.indexOf("토"))
    val additionalDiscount = calculateAdditionalDiscount(isWeekend, orderedItems)
    val christmasDayDiscount = calculateChristmasDayDiscount(currentDate)
    if (christmasDayDiscount > 0)
        benefitsDetails["크리스마스 디데이 할인"] = christmasDayDiscount
    val specialDiscount = calculateSpecialDiscount(currentDate)
    if (specialDiscount > 0)
        benefitsDetails["특별 할인"] = specialDiscount
    val totalDiscount = calculateTotalDiscount(totalAmount, christmasDayDiscount, additionalDiscount, specialDiscount)

    return totalAmount - totalDiscount
}

fun calculateAdditionalDiscount(isWeekend: Boolean, orderedItems: Map<String, Int>): Int {
    var additionalDiscount = 0

    if (isWeekend) {
        val mainMenusCount = orderedItems.filter { main.containsKey(it.key) }.values.sum()
        additionalDiscount = 2023 * mainMenusCount
        if (additionalDiscount > 0) {
            benefitsDetails["주말 할인"] = additionalDiscount
        }
    } else {
        val dessertMenusCount = orderedItems.filter { dessert.containsKey(it.key) }.values.sum()
        additionalDiscount = 2023 * dessertMenusCount
        if (additionalDiscount > 0) {
            benefitsDetails["평일 할인"] = additionalDiscount
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

        if (!isValidDate(date)) {
            println("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.")
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
        val (menuName, quantity) = receiveAndVerifyMenuAndQuantity(menuItem)
        orderedItems[menuName] = quantity
    }

    return orderedItems
}

fun checkOrderValidity(orderedItems: Map<String, Int>) {
    if (orderedItems.all { drink.containsKey(it.key) } && orderedItems.isNotEmpty()) {
        throw DrinksOnlyException("[ERROR] You can't just order drinks")
    }

    if (orderedItems.values.sum() > 20) {
        throw MaximumMenusExceededException("Only up to 20 menus can be ordered. [ERROR]")
    }
}

fun determineBadge(totalBenefitAmount: Int): String {
    return when {
        totalBenefitAmount >= 20000 -> "산타"
        totalBenefitAmount >= 10000 -> "트리"
        totalBenefitAmount >= 5000 -> "별"
        else -> ""
    }
}

fun printOrderSummary(date: String, orderedItems: Map<String, Int>, totalAmount: Int, discountedTotalAmount: Int) {
    val numberFormat = NumberFormat.getNumberInstance(Locale("en")) // Use Locale for comma formatting
    val formattedTotalAmount = numberFormat.format(totalAmount)
    val formattedDiscountedTotalAmount = numberFormat.format(discountedTotalAmount)

    println("12월 $date 일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n")
    println("<주문 메뉴>")
    for ((menuName, quantity) in orderedItems) {
        println("$menuName $quantity 개")
    }
    println("\n<할인 전 총주문 금액>\n$formattedTotalAmount 원")

    val giftMenu = if (totalAmount >= 120000) {
        "샴페인 1개"
    } else {
        "없음"
    }

    if (giftMenu == "샴페인 1개") {
        benefitsDetails["증정 이벤트"] = 25000
    }
    println("\n<증정 메뉴>\n$giftMenu")

    println("\n<혜택 내역>")
    var totalBenefitAmount = 0
    if (benefitsDetails.isEmpty()) {
        println("없음")
    } else {
        for ((benefitName, benefitAmount) in benefitsDetails) {
            val formattedBenefitAmount = NumberFormat.getNumberInstance(Locale("en")).format(benefitAmount)
            totalBenefitAmount += benefitAmount
            println("$benefitName: -$formattedBenefitAmount 원")
        }
    }

    println("\n<총혜택 금액>")
    val formattedTotalBenefitAmount = NumberFormat.getNumberInstance(Locale("en")).format(totalBenefitAmount)
    if (totalBenefitAmount == 0)
        println("0 원")
    else
        println("-$formattedTotalBenefitAmount 원")

    val badge = determineBadge(totalBenefitAmount)
    println("\n<할인 후 예상 결제 금액>\n$formattedDiscountedTotalAmount 원")

    println("\n<12월 이벤트 배지>")
    if (badge.isEmpty())
        println("없음")
    else
        println(badge)
}
