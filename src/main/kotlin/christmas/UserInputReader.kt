package christmas

import camp.nextstep.edu.missionutils.Console

class UserInputReader {
    fun getInputFromUser(): String {
        val newInput = Console.readLine()
        return if (newInput.isNotBlank()) {
            newInput
        } else {
            // Handle the case where the input is blank, possibly by asking the user again.
            getInputFromUser()
        }
    }
}
