package christmas

import camp.nextstep.edu.missionutils.Console

fun isValidDate(input: String): Boolean {
    return try {
        val date = input.toInt()
        date in 1..31
    } catch (e: NumberFormatException) {
        false
    }
}

fun main() {
    val appetizer = mapOf("양송이수프" to 6000, "타파스" to 5500, "시저샐러드" to 8000)
    val main = mapOf("티본스테이크" to 55000, "바비큐립" to 54000, "해산물파스타" to 35000, "크리스마스파스타" to 25000)
    val dessert = mapOf("초코케이크" to 15000, "아이스크림" to 5000)
    val drink = mapOf("제로콜라" to 3000, "레드와인" to 60000, "샴페인" to 25000)

    val days = listOf("목", "금", "토", "일", "월", "화", "수")

    println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.\n" +
            "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)")

    var validDate = false
    var date: String

    do {
        date = Console.readLine()

        if (isValidDate(date)) {
            validDate = true
        } else {
            println("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.")
        }

    } while (!validDate)

    println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)")

    val inputMenu = Console.readLine()
    val menuItems = inputMenu.split(",")

    val orderedItems = mutableSetOf<String>()
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
                        if (orderedItems.contains(menuName)) {
                            println("[ERROR] 중복 메뉴")
                        } else {
                            orderedItems.add(menuName)
                            totalMenusOrdered += quantity
                            println("Ordered: $menuName - Quantity: $quantity")
                        }
                    } else { // The menu entered is not on the menu
                        println("[ERROR] 메뉴판에 없는 메뉴")
                    }
                } else { // quantity less than 1
                    println("[ERROR] 수량이 1 미만")
                }
            }
        } else { // invalid format
            println("[ERROR] 잘못된 형식")
        }
    }
    if (totalMenusOrdered > 20) {
        println("[ERROR] 메뉴는 최대 20개까지만 주문할 수 있습니다.")
    }
    if (orderedItems.all { drink.containsKey(it) } && orderedItems.isNotEmpty()) {
        println("[ERROR] 음료만 주문하실 수 없습니다.")
    }
}
