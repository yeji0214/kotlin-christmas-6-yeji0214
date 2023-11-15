package christmas

data class MenuAndQuantity(var menuName: String, var quantity: Int)

class InputValidation {
    private val dateValidator = DateValidator()
    private val menuValidator = MenuValidator()
    private val userInputReader = UserInputReader()

    fun isValidDate(input: String): Boolean {
        return dateValidator.isValidDate(input)
    }

    fun receiveAndVerifyMenuAndQuantity(input: String): Pair<String, Int> {
        var menuAndQuantity = MenuAndQuantity("", 0)
        var mutableInput = input

        while (true) {
            try {
                val parts = mutableInput.split("-")
                menuAndQuantity = processInputValidation(parts, menuAndQuantity)
                break
            } catch (e: IllegalArgumentException) {
                println(e.message)
                mutableInput = userInputReader.getInputFromUser()
            }
        }

        return menuAndQuantity.menuName to menuAndQuantity.quantity
    }

    private fun processInputValidation(parts: List<String>, menuAndQuantity: MenuAndQuantity): MenuAndQuantity {
        return if (parts.size == 2) {
            val result = menuValidator.extractMenuNameAndQuantity(parts)
            val newMenuAndQuantity = menuAndQuantity.copy(
                    menuName = result.first,
                    quantity = result.second
            )
            if (menuValidator.isValidOrder(newMenuAndQuantity.menuName, newMenuAndQuantity.quantity)) {
                newMenuAndQuantity
            } else {
                throw IllegalArgumentException(MessageConstants.ERROR_INVALID_ORDER)
            }
        } else {
            throw IllegalArgumentException(MessageConstants.ERROR_INVALID_ORDER)
        }
    }
}
