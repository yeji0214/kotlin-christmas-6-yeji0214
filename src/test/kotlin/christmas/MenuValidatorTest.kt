package christmas

import camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest
import camp.nextstep.edu.missionutils.test.NsTest
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
    @Test
    fun `메뉴 수량이 20보다 큰 경우 (여러개의 메뉴)`() {
        val orderProcessing = OrderProcessing()

        assertEquals(false, orderProcessing.checkOrderValidity(mapOf("레드와인" to 10, "크리스마스파스타" to 11)))
    }

    @Test
    fun `메뉴 수량이 20보다 큰 경우 (단일 메뉴)`() {
        val orderProcessing = OrderProcessing()

        assertEquals(false, orderProcessing.checkOrderValidity(mapOf("크리스마스파스타" to 50)))
    }

    @Test
    fun `정상 동작 (메뉴가 20개인 경우)`() {
        val orderProcessing = OrderProcessing()

        assertEquals(true, orderProcessing.checkOrderValidity(mapOf("타파스" to 5, "양송이수프" to 10, "아이스크림" to 5)))
    }

//    @Test
//    fun `중복된 메뉴가 입력된 경우`() {
//        assertSimpleTest {
//            runException("3", "초코케이크-2,제로콜라-1,초코케이크-1")
//            Assertions.assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.")
//        }
//    }

    @Test
    fun `음료만 주문한 경우`() {
        assertSimpleTest {
            runException("3", "제로콜라-1,레드와인-2")
            Assertions.assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.")
        }
    }

    @Test
    fun `메뉴 형식이 다른 경우`() {
        assertSimpleTest {
            runException("10", "제로콜라+1,레드와인-2")
            Assertions.assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.")
        }
    }

    @Test
    fun `메뉴 수량이 숫자가 아닌 경우`() {
        assertSimpleTest {
            runException("10", "제로콜라-a,레드와인-c")
            Assertions.assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.")
        }
    }

    override fun runMain() {
        main()
    }
}