package christmas

import appetizer
import dessert
import drink
import main

class MenuPriceCalculator {
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
}