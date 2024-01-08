fun main() {
    val coffeeList = buildList {
        for (i in 0 until 100) {
            add(makeRandomCoffee())
        }
    }
    val mediumStrongEspressos = coffeeList.filter {
        it.type.isCappuccino() && it.strength.isStrong() && it.size.isMedium()
    }
    println(mediumStrongEspressos)
}

fun makeRandomCoffee(): Coffee {
    return Coffee(
        type = CoffeeType.entries.random(),
        strength = CoffeeStrength.entries.random(),
        size = CoffeeSize.entries.random()
    )
}