package christmas

import camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest
import camp.nextstep.edu.missionutils.test.NsTest
import main
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ResultTest : NsTest() {
    @Test
    fun `정상 동작(주문 금액 만 원 이하, 할인 적용 X)`() {
        assertSimpleTest {
            run("26", "타파스-1,제로콜라-1")
            assertThat(output()).contains(
                    "<주문 메뉴>", "타파스 1개", "제로콜라 1개",
                    "<할인 전 총주문 금액>", "8,500원",
                    "<증정 메뉴>", "없음",
                    "<혜택 내역>", "없음",
                    "<총혜택 금액>", "0원",
                    "<할인 후 예상 결제 금액>", "8,500원",
                    "<12월 이벤트 배지>", "없음"
            )
        }
    }

    @Test
    fun `정상 동작(크리스마스 디데이 할인)`() {
        assertSimpleTest {
            run("5", "양송이수프-2,크리스마스파스타-1")
            assertThat(output()).contains(
                    "<주문 메뉴>", "양송이수프 2개", "크리스마스파스타 1개",
                    "<할인 전 총주문 금액>", "37,000원",
                    "<증정 메뉴>", "없음",
                    "<혜택 내역>", "크리스마스 디데이 할인: -1,400원",
                    "<총혜택 금액>", "-1,400원",
                    "<할인 후 예상 결제 금액>", "35,600원",
                    "<12월 이벤트 배지>", "없음"
            )
        }
    }

    @Test
    fun `정상 동작(크리스마스 디데이 할인, 평일 할인 적용)`() {
        assertSimpleTest {
            run("12", "시저샐러드-1,아이스크림-2")
            assertThat(output()).contains(
                    "<주문 메뉴>", "시저샐러드 1개", "아이스크림 2개",
                    "<할인 전 총주문 금액>", "18,000원",
                    "<증정 메뉴>", "없음",
                    "<혜택 내역>", "평일 할인: -4,046원", "크리스마스 디데이 할인: -2,100원",
                    "<총혜택 금액>", "-6,146원",
                    "<할인 후 예상 결제 금액>", "11,854원",
                    "<12월 이벤트 배지>", "별"
            )
        }
    }

    @Test
    fun `정상 동작(크리스마스 디데이 할인, 평일 할인, 특별 할인 적용)`() {
        assertSimpleTest {
            run("10", "초코케이크-1,샴페인-1,양송이수프-1")
            assertThat(output()).contains(
                    "<주문 메뉴>", "초코케이크 1개", "샴페인 1개", "양송이수프 1개",
                    "<할인 전 총주문 금액>", "46,000원",
                    "<증정 메뉴>", "없음",
                    "<혜택 내역>", "평일 할인: -2,023원", "크리스마스 디데이 할인: -1,900원", "특별 할인: -1,000원",
                    "<총혜택 금액>", "-4,923원",
                    "<할인 후 예상 결제 금액>", "41,077원",
                    "<12월 이벤트 배지>", "없"
            )
        }
    }

    @Test
    fun `정상 동작(모든 할인 적용, 샴페인 증정, 이벤트 배지가 산타)`() {
        assertSimpleTest {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1")
            assertThat(output()).contains(
                    "<주문 메뉴>", "티본스테이크 1개", "바비큐립 1개", "초코케이크 2개", "제로콜라 1개",
                    "<할인 전 총주문 금액>", "142,000원",
                    "<증정 메뉴>", "샴페인 1개",
                    "<혜택 내역>", "평일 할인: -4,046원", "크리스마스 디데이 할인: -1,200원", "특별 할인: -1,000원", "증정 이벤트: -25,000원",
                    "<총혜택 금액>", "-31,246원",
                    "<할인 후 예상 결제 금액>", "135,754원",
                    "<12월 이벤트 배지>", "산타"
            )
        }
    }

    @Test
    fun `정상 동작(주말 할인 적용)`() {
        assertSimpleTest {
            run("29", "바비큐립-1,아이스크림-1")
            assertThat(output()).contains(
                    "<주문 메뉴>", "바비큐립 1개", "아이스크림 1개",
                    "<할인 전 총주문 금액>", "59,000원",
                    "<증정 메뉴>", "없음",
                    "<혜택 내역>", "주말 할인: -2,023원",
                    "<총혜택 금액>", "-2,023원",
                    "<할인 후 예상 결제 금액>", "56,977원",
                    "<12월 이벤트 배지>", "없"
            )
        }
    }

    @Test
    fun `정상 동작(이벤트 배지가 트리)`() {
        assertSimpleTest {
            run("25", "바비큐립-1,아이스크림-1,초코케이크-2")
            assertThat(output()).contains(
                    "<주문 메뉴>", "바비큐립 1개", "아이스크림 1개", "초코케이크 2개",
                    "<할인 전 총주문 금액>", "89,000원",
                    "<증정 메뉴>", "없음",
                    "<혜택 내역>", "평일 할인: -6,069원", "크리스마스 디데이 할인: -3,400원", "특별 할인: -1,000원",
                    "<총혜택 금액>", "-10,469원",
                    "<할인 후 예상 결제 금액>", "78,531원",
                    "<12월 이벤트 배지>", "트"
            )
        }
    }

    @Test
    fun `정상 동작(이벤트 배지가 별)`() {
        assertSimpleTest {
            run("21", "타파스-1,크리스마스파스타-2,제로콜라-5,초코케이크-1")
            assertThat(output()).contains(
                    "<주문 메뉴>", "타파스 1개", "크리스마스파스타 2개", "제로콜라 5개", "초코케이크 1개",
                    "<할인 전 총주문 금액>", "85,500원",
                    "<증정 메뉴>", "없음",
                    "<혜택 내역>", "평일 할인: -2,023원", "크리스마스 디데이 할인: -3,000원",
                    "<총혜택 금액>", "-5,023원",
                    "<할인 후 예상 결제 금액>", "80,477원",
                    "<12월 이벤트 배지>", "별"
            )
        }
    }
    override fun runMain() {
        main()
    }
}
