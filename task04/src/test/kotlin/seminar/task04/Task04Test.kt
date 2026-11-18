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


    @Test fun `экспресс учитывает вес и расстояние`() {
        assertEquals(420, ExpressDelivery("Дом", 2).cost(1))
        assertEquals(720, ExpressDelivery("Дом", 5).cost(4))
        assertEquals(201_100, ExpressDelivery("Дом", 10_000).cost(10))
    }
    @Test fun `экспресс проверяет адрес и расстояние`() {
        for (address in listOf("", " ", "\t")) {
            assertFailsWith<IllegalArgumentException> { ExpressDelivery(address, 1) }
        }
        for (distance in listOf(0, -1)) {
            assertFailsWith<IllegalArgumentException> { ExpressDelivery("Дом", distance) }
        }
        assertEquals(" Дом ", ExpressDelivery(" Дом ", 1).destination)
    }
    @Test fun `экспресс допускает десять килограммов но не одиннадцать`() {
        val delivery: Delivery = ExpressDelivery("Парк", 3)
        assertEquals(1160, delivery.cost(10))
        for (weight in listOf(0, -1, 11, 10_000)) {
            assertFailsWith<IllegalArgumentException> { delivery.cost(weight) }
            assertFailsWith<IllegalArgumentException> { delivery.label(weight) }
        }
        assertEquals("Доставка в Парк: 520 руб.", delivery.label(2))
    }
    @Test fun `один экземпляр рассчитывает разные посылки независимо`() {
        val delivery = CourierDelivery("Школа")
        assertEquals(450, delivery.cost(5))
        assertFailsWith<IllegalArgumentException> { delivery.cost(-2) }
        assertEquals(300, delivery.cost(2))
        assertEquals(450, delivery.cost(5))
    }
    @Test fun `адрес нельзя переприсвоить снаружи`() {
        assertFalse(Delivery::class.java.methods.any { it.name == "setDestination" })
        assertFalse(Delivery::class.java.fields.any {
            it.name == "destination" && !java.lang.reflect.Modifier.isFinal(it.modifiers)
        })
    }
}
