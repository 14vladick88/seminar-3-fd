# 04. Доставка · 20 минут

Темы: абстрактный класс, наследование, `override`, полиморфизм.
Откройте `src/main/kotlin/seminar/task04/Delivery.kt`.

Закончите три класса:

- `abstract class Delivery(val destination: String)` хранит адрес. Пустой адрес или адрес только из пробельных символов запрещён: `IllegalArgumentException`. Допустимый адрес сохраняется без изменений.
- `abstract fun cost(weightKg: Int): Int` рассчитывает стоимость в целых рублях.
- `fun label(weightKg: Int): String` возвращает **точно** `Доставка в <адрес>: <стоимость> руб.`. Стоимость получает вызовом `cost`, без проверки конкретного типа доставки.
- `Pickup(destination: String)` — самовывоз: стоимость 0.
- `CourierDelivery(destination: String)` — курьер: 200 рублей плюс 50 рублей за каждый килограмм.
- Обе реализации `cost` принимают только положительный целый вес, иначе `IllegalArgumentException`. Это относится и к бесплатному самовывозу. `label` с некорректным весом тоже должен выбрасывать исключение через `cost`.

В тестах положительный вес не превышает 10 000 кг.

```kotlin
val deliveries: List<Delivery> = listOf(Pickup("ПВЗ"), CourierDelivery("Общежитие"))
for (delivery in deliveries) println(delivery.label(3))
// Доставка в ПВЗ: 0 руб.
// Доставка в Общежитие: 350 руб.
```

Добавление нового потомка `Delivery` не должно требовать изменения `label`.
Один из тестов передаст собственный вид доставки — это проверяет полиморфизм.
Проверка: `./gradlew :task04:test` (Windows: `.\gradlew.bat :task04:test`).
