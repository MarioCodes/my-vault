package es.msanchez.kotlin.playzone.validator

import org.springframework.stereotype.Component

@Component
class KotlinValidator {

    fun validate(): String {
        return "This is the Kotlin validator"
    }

}