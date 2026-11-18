# 03. Проездной

Откройте [TravelCard.kt](src/main/kotlin/seminar/task03/TravelCard.kt) и закончите класс `TravelCard`.

Контракт:

- `TravelCard(initialTrips: Int)` принимает начальный остаток от 0. Отрицательное значение вызывает `IllegalArgumentException`.
- `remainingTrips: Int` можно прочитать снаружи, но изменить только внутри класса.
- `topUp(trips: Int): Unit` увеличивает остаток. Аргумент должен быть положительным, иначе `IllegalArgumentException`, остаток сохраняется.
- `ride(): Boolean` списывает одну поездку и возвращает `true`, если остаток положительный. При нуле возвращает `false`, остаток остаётся 0.
- Разные экземпляры имеют независимые остатки. Переполнение `Int` проверять не нужно: в тестах общие суммы не превышают 100 000.

```kotlin
val card = TravelCard(1)
println(card.ride())           // true
println(card.ride())           // false
card.topUp(2)
println(card.remainingTrips)   // 2
```

Не заменяйте класс общими переменными или `object`. Сохраните сигнатуры из заготовки.
