package christmas

class DateValidator {
    fun isValidDate(input: String): Boolean {
        return try {
            val date = input.toInt()
            date in 1..31
        } catch (e: NumberFormatException) {
            false
        }
    }
}
