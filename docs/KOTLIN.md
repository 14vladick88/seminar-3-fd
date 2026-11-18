# Мини-шпаргалка

```kotlin
val values: List<Int> = listOf(2, 5, 8)
println(values[0]) // индексы начинаются с 0
var total = 0
for (value in values) {
    total += value
}

fun isPositive(value: Int): Boolean {
    return value > 0
}
```

`&&` — одновременно, `||` — хотя бы одно, `!` — отрицание. `==` сравнивает значения.
`5 / 2` даёт `2`, а `5 / 2.0` — `2.5`.

```kotlin
class Counter(initial: Int) {
    var value = initial
        private set

    init { require(initial >= 0) }

    fun increment() { value++ }
}
```

`val` запрещает переприсваивание ссылки, но методы объекта могут менять его состояние.
`require(false)` выбрасывает `IllegalArgumentException`.

```kotlin
abstract class Animal(val name: String) {
    abstract fun voice(): String
    fun greeting(): String = "$name: ${voice()}"
}
class Cat(name: String) : Animal(name) {
    override fun voice(): String = "Мяу"
}

interface Printer {
    fun print(text: String)
}
class GreetingService(private val printer: Printer) {
    fun greet() { printer.print("Привет!") }
}
```

У `TODO("описание")` нет скрытого решения: при выполнении он выбрасывает `NotImplementedError`.
Замените его своим кодом внутри фигурных скобок. Если функция возвращает значение, используйте `return`.

## Двоичная запись

```kotlin
val bits = 10.toString(2) // "1010"
val value = "1010".toInt(2) // 10
var ones = 0
for (digit in bits) {
    if (digit == '1') ones++
}
```
