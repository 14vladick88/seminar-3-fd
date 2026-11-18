package seminar.task03

import kotlin.test.*

class Task03Test {

    @Test fun `конструктор сохраняет остаток включая ноль`() {
        assertEquals(3, TravelCard(3).remainingTrips)
        assertEquals(0, TravelCard(0).remainingTrips)
    }
    @Test fun `отрицательный начальный остаток запрещён`() {
        assertFailsWith<IllegalArgumentException> { TravelCard(-1) }
    }
    @Test fun `поездка списывает ровно одну поездку`() {
        val card = TravelCard(2)
        assertTrue(card.ride())
        assertEquals(1, card.remainingTrips)
        assertTrue(card.ride())
        assertEquals(0, card.remainingTrips)
    }
    @Test fun `пустая карта не уходит в минус`() {
        val card = TravelCard(0)
        repeat(3) { assertFalse(card.ride()) }
        assertEquals(0, card.remainingTrips)
    }
    @Test fun `пополнение складывается с остатком`() {
        val card = TravelCard(2)
        card.topUp(3)
        assertEquals(5, card.remainingTrips)
    }
    @Test fun `некорректное пополнение не меняет остаток`() {
        val card = TravelCard(2)
        for (amount in listOf(0, -1, -10)) {
            assertFailsWith<IllegalArgumentException> { card.topUp(amount) }
            assertEquals(2, card.remainingTrips)
        }
    }
    @Test fun `после исчерпания можно пополнить и ехать`() {
        val card = TravelCard(1)
        assertTrue(card.ride())
        assertFalse(card.ride())
        card.topUp(2)
        assertTrue(card.ride())
        assertEquals(1, card.remainingTrips)
    }
    @Test fun `экземпляры независимы`() {
        val first = TravelCard(2)
        val second = TravelCard(2)
        first.ride()
        first.topUp(5)
        assertEquals(6, first.remainingTrips)
        assertEquals(2, second.remainingTrips)
    }
    @Test fun `публичного сеттера остатка нет`() {
        assertFalse(TravelCard::class.java.methods.any { it.name == "setRemainingTrips" }, "У remainingTrips должен быть private set")
    }

}
