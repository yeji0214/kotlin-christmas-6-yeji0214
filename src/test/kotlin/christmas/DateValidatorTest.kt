package christmas

import camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest
import camp.nextstep.edu.missionutils.test.NsTest
import main
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DateValidatorTest : NsTest() {
    private val dateValidator = DateValidator()
    @Test
    fun `날짜가 음수인 경우`() {
        assertEquals(false, dateValidator.isValidDate("-1"))
    }

    @Test
    fun `날짜가 1보다 작은 경우`() {
        assertEquals(false, dateValidator.isValidDate("0"))
    }

    @Test
    fun `날짜가 31보다 큰 경우`() {
        assertEquals(false, dateValidator.isValidDate("32"))
    }

    @Test
    fun `날짜가 숫자가 아닌 경우`() {
        assertEquals(false, dateValidator.isValidDate("a"))
    }

    @Test
    fun `날짜가 정상 입력된 경우`() {
        assertEquals(true, dateValidator.isValidDate("10"))
    }

    override fun runMain() {
        main()
    }
}