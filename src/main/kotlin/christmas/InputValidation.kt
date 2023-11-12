package christmas

class MaximumMenusExceededException(message: String) : Exception(message)
class DrinksOnlyException(message: String) : Exception(message)

class InputValidation {
    private val dateValidator = DateValidator()
    private val menuValidator = MenuValidator()
    private val userInputReader = UserInputReader()

    fun isValidDate(input: String): Boolean {
        return dateValidator.isValidDate(input)
    }

    fun receiveAndVerifyMenuAndQuantity(input: String): Pair<String, Int> {
        // Delegate to the MenuValidator
        var validInput = false
        var menuName = ""
        var quantity = 0
        var mutableInput = input

        while (!validInput) {
            val parts = mutableInput.split("-")
            if (parts.size == 2) {
                val result = menuValidator.extractMenuNameAndQuantity(parts)
                menuName = result.first
                quantity = result.second

                if (menuValidator.isValidOrder(menuName, quantity)) {
                    validInput = true
                } else {
                    println(MessageConstants.ERROR_INVALID_ORDER)
                }
            } else {
                println(MessageConstants.ERROR_INVALID_ORDER)
            }

            if (!validInput) {
                mutableInput = userInputReader.getInputFromUser()
            }
        }

        return menuName to quantity
    }
}
