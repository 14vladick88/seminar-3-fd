package seminar.task05

import kotlin.test.*

class Task05Test {
    private class RecordingNotifier : Notifier {
        val messages = mutableListOf<String>()
        override fun send(message: String) { messages.add(message) }
    }

    @Test fun `карта сохраняет начальный остаток и запрещает отрицательный`() {
        assertEquals(0, TravelCard(0).remainingTrips)
        assertEquals(100_000, TravelCard(100_000).remainingTrips)
        assertFailsWith<IllegalArgumentException> { TravelCard(-1) }
    }
    @Test fun `группа списывается целиком включая последний остаток`() {
        val card = TravelCard(6)
        val pass: TravelPass = card
        assertTrue(pass.pay(2))
        assertEquals(4, card.remainingTrips)
        assertTrue(pass.pay(4))
        assertEquals(0, card.remainingTrips)
        assertFalse(pass.pay(1))
        assertEquals(0, card.remainingTrips)
    }
    @Test fun `отказ не списывает даже часть группы`() {
        val card = TravelCard(3)
        assertFalse(card.pay(5))
        assertEquals(3, card.remainingTrips)
        assertTrue(card.pay(2))
        assertEquals(1, card.remainingTrips)
        assertFalse(card.pay(2))
        assertEquals(1, card.remainingTrips)
    }
    @Test fun `оба проездных отклоняют некорректное число пассажиров`() {
        val card = TravelCard(5)
        for (pass in listOf<TravelPass>(card, UnlimitedPass())) {
            for (passengers in listOf(0, -1, -5)) {
                assertFailsWith<IllegalArgumentException> { pass.pay(passengers) }
            }
        }
        assertEquals(5, card.remainingTrips)
    }
    @Test fun `безлимитный проездной работает повторно с большими группами`() {
        val pass: TravelPass = UnlimitedPass()
        repeat(3) { assertTrue(pass.pay(100_000)) }
        assertTrue(pass.pay(1))
    }
    @Test fun `остатки разных карт независимы и недоступны для записи`() {
        val first = TravelCard(4)
        val second = TravelCard(4)
        first.pay(3)
        assertEquals(1, first.remainingTrips)
        assertEquals(4, second.remainingTrips)
        assertFalse(TravelCard::class.java.methods.any { it.name == "setRemainingTrips" })
        assertFalse(TravelCard::class.java.fields.any {
            it.name == "remainingTrips" && !java.lang.reflect.Modifier.isFinal(it.modifiers)
        })
    }
    @Test fun `сервис сообщает об одной успешной оплате всей группы`() {
        val notifier = RecordingNotifier()
        val card = TravelCard(9)
        assertTrue(TripService(notifier).travel(card, 3))
        assertEquals(6, card.remainingTrips)
        assertEquals(listOf("Поездка оплачена. Пассажиров: 3"), notifier.messages)
    }
    @Test fun `после отказа можно оплатить меньшую группу`() {
        val notifier = RecordingNotifier()
        val service = TripService(notifier)
        val card = TravelCard(2)
        assertFalse(service.travel(card, 3))
        assertTrue(notifier.messages.isEmpty())
        assertEquals(2, card.remainingTrips)
        assertTrue(service.travel(card, 2))
        assertFalse(service.travel(card, 1))
        assertEquals(listOf("Поездка оплачена. Пассажиров: 2"), notifier.messages)
    }
    @Test fun `сервис работает с разными видами проездных`() {
        val notifier = RecordingNotifier()
        val service = TripService(notifier)
        val card = TravelCard(1)
        assertTrue(service.travel(card, 1))
        assertTrue(service.travel(UnlimitedPass(), 12))
        assertFalse(service.travel(card, 1))
        assertEquals(listOf("Поездка оплачена. Пассажиров: 1", "Поездка оплачена. Пассажиров: 12"), notifier.messages)
    }
    @Test fun `сервис передаёт неизвестному проездному всю группу одним вызовом`() {
        val calls = mutableListOf<Int>()
        val pass = object : TravelPass {
            override fun pay(passengers: Int): Boolean {
                calls.add(passengers)
                return passengers == 6
            }
        }
        val notifier = RecordingNotifier()
        val service = TripService(notifier)
        assertTrue(service.travel(pass, 6))
        assertFalse(service.travel(pass, 2))
        assertEquals(listOf(6, 2), calls)
        assertEquals(listOf("Поездка оплачена. Пассажиров: 6"), notifier.messages)
    }
    @Test fun `при неверном количестве сервис не обращается к зависимостям`() {
        val pass = object : TravelPass {
            override fun pay(passengers: Int): Boolean = error("Проездной не должен вызываться")
        }
        val notifier = object : Notifier {
            override fun send(message: String) = error("Уведомитель не должен вызываться")
        }
        for (passengers in listOf(0, -3)) {
            assertFailsWith<IllegalArgumentException> { TripService(notifier).travel(pass, passengers) }
        }
    }
    @Test fun `уведомление отправляется только после оплаты`() {
        val card = TravelCard(5)
        var calls = 0
        val notifier = object : Notifier {
            override fun send(message: String) {
                calls++
                assertEquals(2, card.remainingTrips)
                assertEquals("Поездка оплачена. Пассажиров: 3", message)
            }
        }
        assertTrue(TripService(notifier).travel(card, 3))
        assertEquals(1, calls)
    }
}
