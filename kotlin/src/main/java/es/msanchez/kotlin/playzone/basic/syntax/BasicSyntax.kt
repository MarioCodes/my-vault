package es.msanchez.kotlin.playzone.basic.syntax

/**
 * examples from: https://kotlinlang.org/docs/reference/basic-syntax.html
 */
class BasicSyntax {

    class Classes {

        fun createClass() {
            val functions = Functions() // no 'new' required
        }

    }

    class Functions {

        // 2 int parameters and returns an int
        fun sum(a: Int, b: Int): Int {
            return a + b
        }

        // inferred return type
        fun substract(a: Int, b: Int) = a - b

        // Unit is same as void (?)
        fun voidFunction(): Unit {
            println("")
        }

        // Unit can be omitted
        fun voidFunctionOmitted() {

        }

    }

    val topLevelVariable: String = "oh no"

    fun variables() {
        // same as final
        val readOnly: Int = 1
        val inferredType = 2

        var realVariable: String = "oh no"
    }

    fun stringTemplates() {
        var a = 1
        val string1 = "a is $a".also(::println)

        a = 2
        val string2 = "${string1.replace("is", "was")}, but now is $a".also(::println)
    }

    fun conditionalExpressions(a: Int, b: Int) = if (a > b) a else b

    class Nullables {

        // references have to be explicitely set as nullable if this is possible.
        fun parseInt(str: String): Int? {
            return str.toIntOrNull()
        }

        fun useNullableFunction(arg1: String, arg2: String) {
            val x = parseInt(arg1) // this may return null
            val y = parseInt(arg2)

            // you cannot multiply them, as they may be null
            if (x != null && y != null) {
                // they're automatically casted to non nullable after check
                println(x * y)
            }
        }
    }

    // isOperator
    fun getStringLength(obj: Any): Int? {
        if (obj !is String)
            return null

        // obj is automatically casted to String here
        return obj.length
    }

    class Collections {

        val items = listOf("apple", "banana")

        fun foorLoop() {
            for (item in items) {
                println(item)
            }
        }

        fun lambdas() {
            items.filter { it.startsWith("banana") }
                    .sortedBy { it }
                    .forEach(::println)
        }

        fun whileLoop() {
            var index = 0
            while (index < items.size) {
                println("item at index $index is ${items[index]}")
                index++
            }
        }

    }

    fun whenExpression(obj: Any): String =
            when (obj) {
                1 -> "one"
                "Hello" -> "Greeting"
                is Long -> "long"
                else -> "anything"
            }

    fun ranges() {
        val x = 10
        if (x in 1..20) {
            println("fits in range")
        }

        if (x !in 30..40) {
            println("out of range")
        }
    }

}