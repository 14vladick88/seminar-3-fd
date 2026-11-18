package seminar.task01

import kotlin.test.*

class Task01Test {
    @Test fun `чётное число получает два нуля`() {
        assertEquals(24, transform(6))
        assertEquals(8, transform(2))
    }
    @Test fun `нечётное число получает две единицы`() {
        assertEquals(23, transform(5))
        assertEquals(39, transform(9))
    }
    @Test fun `границы диапазона N`() {
        assertEquals(7, transform(1))
        assertEquals(40_000, transform(10_000))
    }
    @Test fun `преобразование чисел разной длины`() {
        for (n in 1..128) {
            assertEquals(4 * n + if (n % 2 == 0) 0 else 3, transform(n), "N=$n")
        }
    }
    @Test fun `поиск возвращает N а не R`() {
        assertEquals(32, maxNBelow(134))
        assertEquals(33, maxNBelow(136))
    }
    @Test fun `строгая граница и отсутствие подходящих чисел`() {
        assertEquals(0, maxNBelow(1))
        assertEquals(0, maxNBelow(7))
        assertEquals(1, maxNBelow(8))
        assertEquals(2, maxNBelow(9))
        assertEquals(32, maxNBelow(135))
    }
    @Test fun `поиск у верхней границы и повторные вызовы`() {
        assertEquals(9_999, maxNBelow(40_000))
        assertEquals(1, maxNBelow(8))
        assertEquals(32, maxNBelow(134))
    }
}
