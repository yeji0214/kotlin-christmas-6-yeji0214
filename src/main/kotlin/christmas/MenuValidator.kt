package christmas


class MenuValidator {
    fun isValidOrder(menuName: String, quantity: Int): Boolean {
        return quantity >= 1 && (MenuData.appetizer.containsKey(menuName) || MenuData.mainMenu.containsKey(menuName) ||
                MenuData.dessert.containsKey(menuName) || MenuData.drink.containsKey(menuName))
    }

    fun extractMenuNameAndQuantity(parts: List<String>): Pair<String, Int> {
        val menuName = parts[0].trim()
        val quantity = parts[1].trim().toIntOrNull() ?: 0
        return menuName to quantity
    }
}
