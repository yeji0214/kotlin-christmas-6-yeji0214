package christmas

import appetizer
import dessert
import drink
import main


class MenuValidator {
    fun isValidOrder(menuName: String, quantity: Int): Boolean {
        return quantity >= 1 && (appetizer.containsKey(menuName) || main.containsKey(menuName) ||
                dessert.containsKey(menuName) || drink.containsKey(menuName))
    }

    fun extractMenuNameAndQuantity(parts: List<String>): Pair<String, Int> {
        val menuName = parts[0].trim()
        val quantity = parts[1].trim().toIntOrNull() ?: 0
        return menuName to quantity
    }
}
