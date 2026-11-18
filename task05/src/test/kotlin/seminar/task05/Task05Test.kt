package seminar.task05

import kotlin.test.*

class Task05Test {

    private class RecordingNotifier : Notifier {
        val messages = mutableListOf<String>()
        override fun send(message: String) { messages.add(message) }
    }
    @Test fun `успех списывает поездку и отправляет сообщение с новым остатком`() {
        val notifier = RecordingNotifier()
        val card = TravelCard(3)
        assertTrue(TripService(notifier).travel(card))
        assertEquals(2, card.remainingTrips)
        assertEquals(listOf("Поездка оплачена. Осталось поездок: 2"), notifier.messages)
    }
    @Test fun `отказ не отправляет уведомление`() {
        val notifier = RecordingNotifier()
        val card = TravelCard(0)
        assertFalse(TripService(notifier).travel(card))
        assertEquals(0, card.remainingTrips)
        assertTrue(notifier.messages.isEmpty())
    }
    @Test fun `последняя поездка успешна а следующая нет`() {
        val notifier = RecordingNotifier()
        val service = TripService(notifier)
        val card = TravelCard(1)
        assertTrue(service.travel(card))
        assertFalse(service.travel(card))
        assertEquals(listOf("Поездка оплачена. Осталось поездок: 0"), notifier.messages)
    }
    @Test fun `на каждый успех приходится ровно одно сообщение`() {
        val notifier = RecordingNotifier()
        val service = TripService(notifier)
        val card = TravelCard(2)
        assertTrue(service.travel(card))
        assertTrue(service.travel(card))
        assertEquals(listOf("Поездка оплачена. Осталось поездок: 1", "Поездка оплачена. Осталось поездок: 0"), notifier.messages)
    }
    @Test fun `сервис использует переданный интерфейс после изменения карты`() {
        val card = TravelCard(1)
        var calls = 0
        val notifier = object : Notifier {
            override fun send(message: String) {
                calls++
                assertEquals(0, card.remainingTrips, "Сначала списание, потом отправка")
                assertEquals("Поездка оплачена. Осталось поездок: 0", message)
            }
        }
        assertTrue(TripService(notifier).travel(card))
        assertEquals(1, calls)
    }
    @Test fun `один сервис работает с разными картами`() {
        val notifier = RecordingNotifier()
        val service = TripService(notifier)
        val first = TravelCard(1)
        val second = TravelCard(3)
        assertTrue(service.travel(first))
        assertTrue(service.travel(second))
        assertEquals(0, first.remainingTrips)
        assertEquals(2, second.remainingTrips)
        assertEquals(2, notifier.messages.size)
    }

}
