package es.msanchez.kotlin.skeleton.controller

import es.msanchez.kotlin.skeleton.validator.KotlinValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KotlinRestController {

    @Autowired
    lateinit var kotlinValidator: KotlinValidator

    @RequestMapping("/kotlin")
    fun index(): String {
        return kotlinValidator.validate()
    }

}
