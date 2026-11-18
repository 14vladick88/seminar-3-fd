package seminar.task05

// Готовые типы для независимости от task03. Их изменять не нужно.
interface Notifier {
    fun send(message: String)
}

class TravelCard(initialTrips: Int) {
    var remainingTrips: Int = initialTrips
        private set
    init { require(initialTrips >= 0) }
    fun ride(): Boolean {
        if (remainingTrips == 0) return false
        remainingTrips--
        return true
    }
}

// Реализуйте только этот класс.
class TripService(private val notifier: Notifier) {
    fun travel(card: TravelCard): Boolean = TODO("Выполните поездку и сообщите об успехе")
}
