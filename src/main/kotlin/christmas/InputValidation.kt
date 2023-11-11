import camp.nextstep.edu.missionutils.Console

class MaximumMenusExceededException(message: String) : Exception(message)
class DrinksOnlyException(message: String) : Exception(message)

fun isValidDate(input: String): Boolean {
    try {
        val date = input.toInt()
        return date in 1..31
    } catch (e: NumberFormatException) {
        return false
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
            val result = extractMenuNameAndQuantity(parts)
            menuName = result.first
            quantity = result.second

            if (isValidOrder(menuName, quantity)) {
                validInput = true
            } else {
                println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.")
            }
        } else {
            println("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.")
        }

        if (!validInput) {
            mutableInput = getInputFromUser()
        }
    }

    return menuName to quantity
}

fun extractMenuNameAndQuantity(parts: List<String>): Pair<String, Int> {
    val menuName = parts[0].trim()
    val quantity = parts[1].trim().toIntOrNull() ?: 0
    return menuName to quantity
}

fun isValidOrder(menuName: String, quantity: Int): Boolean {
    return quantity >= 1 && (appetizer.containsKey(menuName) || main.containsKey(menuName) ||
            dessert.containsKey(menuName) || drink.containsKey(menuName))
}

fun getInputFromUser(): String {
    val newInput = Console.readLine()
    return if (newInput.isNotBlank()) {
        newInput
    } else {
        // Handle the case where the input is blank, possibly by asking the user again.
        getInputFromUser()
    }
}
