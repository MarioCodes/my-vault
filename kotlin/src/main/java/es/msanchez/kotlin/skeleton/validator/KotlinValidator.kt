package es.msanchez.kotlin.skeleton.validator

import org.springframework.stereotype.Component

@Component
class KotlinValidator {

    fun validate(): String {
        return "This is the Kotlin validator"
    }

}