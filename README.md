# KSP Case Detection

## Description

A basic KSP example inspired by [CaseDetectionMacro.swift](https://github.com/apple/swift-syntax/blob/7131e3b316875973abd7a0f45478d710f680143d/Examples/Sources/MacroExamples/Implementation/Member/CaseDetectionMacro.swift), which creates case detection for each kotlin enum entry using kotlin extensions

## Example

~~~kotlin
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

// each enum should be annotated with @CaseDetection

fun makeRandomCoffee(): Coffee {
    return Coffee(
        type = CoffeeType.entries.random(),
        strength = CoffeeStrength.entries.random(),
        size = CoffeeSize.entries.random()
    )
}
~~~

## Acknowledgments
There are a few points to be improved such as code generation template, string case parsing, and others that were overlooked for the sake of simplicity for this proof of concept (for now).
