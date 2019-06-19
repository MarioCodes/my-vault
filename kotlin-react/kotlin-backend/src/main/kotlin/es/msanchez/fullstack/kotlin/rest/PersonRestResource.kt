package es.msanchez.fullstack.kotlin.rest

import es.msanchez.fullstack.kotlin.entity.Person
import es.msanchez.fullstack.kotlin.exception.DataTransferException
import es.msanchez.fullstack.kotlin.service.PersonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/person")
class PersonRestResource(private val personService: PersonService) {

    @GetMapping("create/{name}")
    fun createPerson(@PathVariable("name") name: String) {
        if (!this.personService.isValid(name)) {
            throw DataTransferException("Tried to create a person with a wrong name.")
        }

        this.personService.save(this.create(name))
    }

    internal fun create(name: String): Person {
        val person = Person()
        person.name = name
        return person
    }
}