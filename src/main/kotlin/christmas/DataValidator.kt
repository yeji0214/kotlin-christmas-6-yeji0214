package christmas

class DateValidator {
    fun isValidDate(input: String): Boolean {
        try {
            val date = input.toInt()
            return date in 1..31
        } catch (e: NumberFormatException) {
            return false
        }
    }
}
