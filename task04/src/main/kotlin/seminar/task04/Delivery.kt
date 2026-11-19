package seminar.task04


abstract class Delivery(
    val destination: String
) {
    init {
        require(destination.isNotBlank())
    }

    abstract fun cost(weightKg: Int): Int

    fun label(weightKg: Int): String {
        val price = cost(weightKg)
        return "Доставка в $destination: $price"
    }
}



