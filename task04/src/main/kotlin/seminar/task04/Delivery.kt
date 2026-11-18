package seminar.task04

abstract class Delivery(val destination: String) {
    init {
        // TODO: проверьте адрес.
    }

    abstract fun cost(weightKg: Int): Int

    fun label(weightKg: Int): String {
        TODO("Используйте полиморфный вызов cost")
    }
}

class Pickup(destination: String) : Delivery(destination) {
    override fun cost(weightKg: Int): Int {
        TODO("Стоимость самовывоза")
    }
}

class CourierDelivery(destination: String) : Delivery(destination) {
    override fun cost(weightKg: Int): Int {
        TODO("Стоимость курьерской доставки")
    }
}
