package christmas

import camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest
import camp.nextstep.edu.missionutils.test.NsTest
import main
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MenuValidatorTest : NsTest() {
    @Test
    fun `정상적으로 메뉴와 수량 반환`() {
        val inputValidation = InputValidation()
        val input = "레드와인-1"
        val expectedMenuName = "레드와인"
        val expectedQuantity = 1

        val (menuName, quantity) = inputValidation.receiveAndVerifyMenuAndQuantity(input)

        assertEquals(expectedMenuName, menuName)
        assertEquals(expectedQuantity, quantity)
    }

    @Test
    fun `메뉴판에 없는 메뉴 입력`() {
        val menuValidator = MenuValidator()

        assertEquals(false, menuValidator.isValidOrder("붕어빵", 3))
    }

    @Test
    fun `메뉴 수량이 음수인 경우`() {
        val menuValidator = MenuValidator()

        assertEquals(false, menuValidator.isValidOrder("레드와인", -1))
    }

    @Test
    fun `메뉴 수량이 1보다 작은 경우`() {
        val menuValidator = MenuValidator()

        assertEquals(false, menuValidator.isValidOrder("레드와인", 0))
    }


    @Test
    fun `정상적으로 메뉴 입력`() {
        val menuValidator = MenuValidator()

        assertEquals(true, menuValidator.isValidOrder("레드와인", 3))
    }
//    @Test
//    fun `메뉴 수량이 20보다 큰 경우`() {
//        val menuValidator = MenuValidator()
//
//        assertEquals(false, menuValidator.isValidOrder("레드와인", 21))
//    }


    override fun runMain() {
        main()
    }
}