import camp.nextstep.edu.missionutils.Console
import java.text.NumberFormat
import java.util.*

class InvalidDateException(message: String) : Exception(message)
class DuplicateMenuException(message: String) : Exception(message)
class InvalidMenuException(message: String) : Exception(message)
class InvalidQuantityException(message: String) : Exception(message)
class InvalidFormatException(message: String) : Exception(message)
class MaximumMenusExceededException(message: String) : Exception(message)
class DrinksOnlyException(message: String) : Exception(message)

fun isValidDate(input: String): Boolean {
    return try {
        val date = input.toInt()
        date in 1..31
    } catch (e: NumberFormatException) {
        false
    }
}

fun receiveAndVerifyMenuAndQuantity(input: String): Pair<String, Int> {
    var validInput = false
    var menuName = ""
    var quantity = 0
    var mutableInput = input

    while (!validInput) {
        val parts = mutableInput.split("-")

        if (parts.size == 2) {
            menuName = parts[0].trim()
            quantity = parts[1].trim().toIntOrNull() ?: 0

            if (quantity >= 1 && (appetizer.containsKey(menuName) || main.containsKey(menuName) ||
                            dessert.containsKey(menuName) || drink.containsKey(menuName))) {
                validInput = true
            } else {
                println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.")
            }
        } else {
            println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.")
        }

        if (!validInput) {
            val newInput = Console.readLine()
            if (newInput.isNotBlank()) {
                mutableInput = newInput
            }
        }
    }

    return menuName to quantity
}

val appetizer = mapOf("양송이수프" to 6000, "타파스" to 5500, "시저샐러드" to 8000)
val main = mapOf("티본스테이크" to 55000, "바비큐립" to 54000, "해산물파스타" to 35000, "크리스마스파스타" to 25000)
val dessert = mapOf("초코케이크" to 15000, "아이스크림" to 5000)
val drink = mapOf("제로콜라" to 3000, "레드와인" to 60000, "샴페인" to 25000)

val days = listOf("목", "금", "토", "일", "월", "화", "수")

val benefitsDetails = mutableMapOf<String, Int>()

fun main() {

    println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.\n" +
            "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)")

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

    println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)")

    val inputMenu = Console.readLine()
    val menuItems = inputMenu.split(",")
    val orderedItems = mutableMapOf<String, Int>()
    var totalMenusOrdered = 0

    for (menuItem in menuItems) {
        val (menuName, quantity) = receiveAndVerifyMenuAndQuantity(menuItem)
        orderedItems[menuName] = quantity
        totalMenusOrdered += quantity
    }

    if (orderedItems.all { drink.containsKey(it.key) } && orderedItems.isNotEmpty()) {
        throw DrinksOnlyException("[ERROR] You can't just order drinks")
    }

    if (totalMenusOrdered > 20) {
        throw MaximumMenusExceededException("Only up to 20 menus can be ordered. [ERROR]")
    }

    val currentDate = date.toInt()
    val totalAmount = calculateTotalAmount(orderedItems, appetizer, main, dessert, drink)
    val discountedTotalAmount = applyDiscounts(currentDate, totalAmount, days, orderedItems)

    val numberFormat = NumberFormat.getNumberInstance(Locale("en")) // Use Locale for comma formatting
    val formattedTotalAmount = numberFormat.format(totalAmount)
    val formattedDiscountedTotalAmount = numberFormat.format(discountedTotalAmount)

    println("12월 ${date}일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n")
    println("<주문 메뉴>")
    for ((menuName, quantity) in orderedItems) {
        println("${menuName} ${quantity}개")
    }
    println("\n<할인 전 총주문 금액>\n${formattedTotalAmount}원")

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
    if (benefitsDetails.size == 0) {
        println("없음")
    }
    else {
        for ((benefitName, benefitAmount) in benefitsDetails) {
            val formattedBenefitAmount = NumberFormat.getNumberInstance(Locale("en")).format(benefitAmount)
            totalBenefitAmount += benefitAmount
            println("${benefitName}: -${formattedBenefitAmount}원")
        }
    }

    println("\n<총혜택 금액>")
    val formattedTotalBenefitAmount = NumberFormat.getNumberInstance(Locale("en")).format(totalBenefitAmount)
    if (totalBenefitAmount == 0)
        println("0원")
    else
        println("-${formattedTotalBenefitAmount}원")

    var Badge = ""
    if (totalBenefitAmount >= 20000)
        Badge = "산타"
    else if (totalBenefitAmount >= 10000)
        Badge = "트리"
    else if (totalBenefitAmount >= 5000)
        Badge = "별"

    println("\n<할인 후 예상 결제 금액>\n${formattedDiscountedTotalAmount}원")

    println("\n<12월 이벤트 배지>")
    if (Badge == "")
        println("없음")
    else
        println(Badge)
}

fun calculateTotalAmount(orderedItems: Map<String, Int>, appetizer: Map<String, Int>, main: Map<String, Int>, dessert: Map<String, Int>, drink: Map<String, Int>): Int {
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
    var additionalDiscount = 0

    // Check for weekend discount
    if (isWeekend) {
        val mainMenusCount = orderedItems.filter { main.containsKey(it.key) }.values.sum()
        additionalDiscount = 2023 * mainMenusCount
        if (additionalDiscount > 0)
            benefitsDetails["주말 할인"] = additionalDiscount
    } else {
        val dessertMenusCount = orderedItems.filter { dessert.containsKey(it.key) }.values.sum()
        additionalDiscount = 2023 * dessertMenusCount
        if (additionalDiscount > 0)
            benefitsDetails["평일 할인"] = additionalDiscount
    }

    val christmasDayDiscount = if (currentDate in 1..25) {
        1000 + (currentDate - 1) * 100
    } else {
        0
    }
    if (christmasDayDiscount > 0)
        benefitsDetails["크리스마스 디데이 할인"] = christmasDayDiscount

    // Check for special discount
    val specialDiscount = if (currentDate % 7 == 3 || currentDate == 25) {
        1000
    } else {
        0
    }
    if (specialDiscount > 0)
        benefitsDetails["특별 할인"] = specialDiscount

    val totalDiscount = if (totalAmount > 10000) {
        christmasDayDiscount + additionalDiscount + specialDiscount
    } else {
        0
    }

    return totalAmount - totalDiscount
}
