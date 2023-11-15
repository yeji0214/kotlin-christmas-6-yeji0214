package christmas

class MenuPriceCalculator {
    fun calculateTotalAmount(orderedItems: Map<String, Int>): Int {
        var totalAmount = 0
        for ((menuName, quantity) in orderedItems) {
            val price = when {
                MenuData.appetizer.containsKey(menuName) -> MenuData.appetizer.getValue(menuName)
                MenuData.mainMenu.containsKey(menuName) -> MenuData.mainMenu.getValue(menuName)
                MenuData.dessert.containsKey(menuName) -> MenuData.dessert.getValue(menuName)
                MenuData.drink.containsKey(menuName) -> MenuData.drink.getValue(menuName)
                else -> 0
            }
            totalAmount += price * quantity
        }
        return totalAmount
    }
}