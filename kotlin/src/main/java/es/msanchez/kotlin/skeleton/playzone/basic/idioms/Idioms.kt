package es.msanchez.kotlin.skeleton.playzone.basic.idioms

import org.springframework.stereotype.Controller
import java.io.File
import java.lang.ArithmeticException
import java.nio.file.Files

@Controller
class Idioms {

    fun defaultValues(arg1: Int = 0, arg2: String = "oh no") {

    }

    fun filterList() {
        val list = listOf(-2, -1, 0, 1, 2)
        val positives = list.filter { x -> x > 0 }
        val positivesShorter = list.filter { it > 0 } // (?)
    }

    fun isInstanceOf(obj: Any) {
        when(obj) {
            is String -> "str"
            is Integer -> "int"
            else -> "whatever"
        }
    }

    fun ranges(value: Int) {
        for(i in 1..10) {
            // includes 10
        }

        for(i in 1 until 10) {
            // does not include 10
        }

        for (i in 2..10 step 2) {
            // goes in steps of i+=2
        }

        for (i in 10 downTo 1) {
            // from 10 to 1, both included
        }

        if(value in 1..10) {
            println(value)
        }
    }

    fun lazyProperty() {
        val p: String by lazy {
            "compute the String"
        }
    }

    fun executeIfNotNull(str: String?) {
        str?.let {
            println("is not null!")
        }
    }

    fun ifNotNull() {
        val files = File("test").listFiles()
        println(files?.size)
    }

    fun ifNotNullElse() {
        val files = File("test").listFiles()
        println(files?.size ?: "empty")
    }

    fun getFirstItem() {
        val emails = emptyList<String>()
        val mainEmail = emails.firstOrNull() ?: ""
    }

    fun ifExpression(arg1: Int) {
        val result = if(arg1 == 1) {
            "one"
        } else if(arg1 == 2) {
            "two"
        } else {
            "three"
        }
    }

    fun tryCatchExpression(arg1: Int) {
        val result = try {
            arg1 / 0
        } catch (ex: ArithmeticException) {
            throw IllegalStateException(ex)
        }
    }

}