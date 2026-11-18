package seminar.task02

import kotlin.test.*

class Task02Test {
    @Test fun `нечётная сумма цифр исходной записи`() {
        assertEquals(30, transform(7))
        assertEquals(114, transform(28))
    }
    @Test fun `чётная сумма цифр исходной записи`() {
        assertEquals(12, transform(3))
        assertEquals(20, transform(5))
    }
    @Test fun `второй бит вычисляется по обновлённой записи`() {
        assertEquals(6, transform(1))
        assertEquals(18, transform(4))
    }
    @Test fun `чётность числа не равна чётности суммы битов`() {
        assertEquals(10, transform(2))
        assertEquals(36, transform(9))
    }
    @Test fun `числа разной длины`() {
        for (n in 1..128) {
            val expected = n * 4 + (Integer.bitCount(n) % 2) * 2
            assertEquals(expected, transform(n), "N=$n")
        }
    }
    @Test fun `верхняя граница преобразования`() {
        assertEquals(40_002, transform(10_000))
    }
    @Test fun `поиск возвращает минимальное N`() {
        assertEquals(21, minNAbove(85))
        assertEquals(25, minNAbove(97))
    }
    @Test fun `результат должен строго превышать порог`() {
        assertEquals(2, minNAbove(6))
        assertEquals(4, minNAbove(12))
        assertEquals(8, minNAbove(30))
    }
    @Test fun `нулевой порог и порог ниже первого результата`() {
        assertEquals(1, minNAbove(0))
        assertEquals(1, minNAbove(5))
    }
    @Test fun `верхняя граница поиска и независимость вызовов`() {
        assertEquals(10_000, minNAbove(39_999))
        assertEquals(1, minNAbove(0))
        assertEquals(21, minNAbove(85))
    }
}
