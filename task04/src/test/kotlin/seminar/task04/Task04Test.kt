package seminar.task04

import kotlin.test.*

class Task04Test {

    @Test fun `адрес сохраняется без обрезания пробелов`() {
        assertEquals(" ПВЗ ", Pickup(" ПВЗ ").destination)
    }
    @Test fun `пустой и пробельный адрес запрещены`() {
        for (address in listOf("", "   ", "\t\n")) {
            assertFailsWith<IllegalArgumentException> { Pickup(address) }
            assertFailsWith<IllegalArgumentException> { CourierDelivery(address) }
        }
    }
    @Test fun `самовывоз бесплатен`() {
        val pickup = Pickup("ПВЗ")
        assertEquals(0, pickup.cost(1))
        assertEquals(0, pickup.cost(10_000))
    }
    @Test fun `курьер учитывает базовую стоимость и каждый килограмм`() {
        val courier = CourierDelivery("Дом")
        assertEquals(250, courier.cost(1))
        assertEquals(350, courier.cost(3))
        assertEquals(500_200, courier.cost(10_000))
    }
    @Test fun `неположительный вес запрещён для обеих доставок`() {
        for (delivery in listOf(Pickup("ПВЗ"), CourierDelivery("Дом"))) {
            for (weight in listOf(0, -1)) {
                assertFailsWith<IllegalArgumentException> { delivery.cost(weight) }
                assertFailsWith<IllegalArgumentException> { delivery.label(weight) }
            }
        }
    }
    @Test fun `общий метод формирует точные подписи`() {
        val deliveries: List<Delivery> = listOf(Pickup("ПВЗ"), CourierDelivery("Общежитие"))
        assertEquals("Доставка в ПВЗ: 0 руб.", deliveries[0].label(3))
        assertEquals("Доставка в Общежитие: 350 руб.", deliveries[1].label(3))
    }
    @Test fun `новый потомок работает без изменения базового класса`() {
        val delivery: Delivery = object : Delivery("Луна") {
            override fun cost(weightKg: Int) = 7 * weightKg
        }
        assertEquals("Доставка в Луна: 28 руб.", delivery.label(4))
    }

}
