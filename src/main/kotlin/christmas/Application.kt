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

val appetizer = mapOf("양송이수프" to 6000, "타파스" to 5500, "시저샐러드" to 8000)
val main = mapOf("티본스테이크" to 55000, "바비큐립" to 54000, "해산물파스타" to 35000, "크리스마스파스타" to 25000)
val dessert = mapOf("초코케이크" to 15000, "아이스크림" to 5000)
val drink = mapOf("제로콜라" to 3000, "레드와인" to 60000, "샴페인" to 25000)

val days = listOf("목", "금", "토", "일", "월", "화", "수")
fun main() {

    println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.\n" +
            "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)")

    var validDate = false
    var date: String

    do {
        date = Console.readLine()

        if (!isValidDate(date)) {
            throw InvalidDateException("[ERROR] Invalid date. Please re-enter.")
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
        val parts = menuItem.split("-")
        if (parts.size == 2) {
            val menuName = parts[0].trim()
            val quantity = parts[1].trim().toIntOrNull()

            if (quantity != null) {
                if (quantity >= 1) {
                    if (appetizer.containsKey(menuName) || main.containsKey(menuName) ||
                            dessert.containsKey(menuName) || drink.containsKey(menuName)) {
                        if (orderedItems.containsKey(menuName)) {
                            throw DuplicateMenuException("[ERROR] Duplicate menu")
                        } else {
                            orderedItems[menuName] = quantity
                            totalMenusOrdered += quantity
                        }
                    } else {
                        throw InvalidMenuException("[ERROR] Invalid menu: $menuName")
                    }
                } else {
                    throw InvalidQuantityException("[ERROR] Quantity less than 1 for: $menuName")
                }
            } else {
                throw InvalidFormatException("[ERROR] Invalid format: $menuItem")
            }
        } else {
            throw InvalidFormatException("[ERROR] Invalid format: $menuItem")
        }
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

    println("\n<증정 메뉴>\n$giftMenu")

    println("\n<할인 후 예상 결제 금액>\n${formattedDiscountedTotalAmount}원")
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
    } else {
        val dessertMenusCount = orderedItems.filter { dessert.containsKey(it.key) }.values.sum()
        additionalDiscount = 2023 * dessertMenusCount
    }

    val christmasDayDiscount = if (currentDate in 1..25) {
        1000 + (currentDate - 1) * 100
    } else {
        0
    }

    // Check for special discount
    val specialDiscount = if (currentDate % 7 == 3 || currentDate == 25) {
        1000
    } else {
        0
    }

    val totalDiscount = if (totalAmount > 10000) {
        christmasDayDiscount + additionalDiscount + specialDiscount
    } else {
        0
    }

    return totalAmount - totalDiscount
}
