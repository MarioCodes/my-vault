package es.msanchez.fullstack.kotlin.service

import es.msanchez.fullstack.kotlin.dao.PersonDao
import es.msanchez.fullstack.kotlin.entity.Person
import org.springframework.stereotype.Service

@Service
class PersonService(private val personDao: PersonDao) {

    fun isValid(name: String): Boolean {
        val person = this.personDao.findOneByName(name)
        return !person.isPresent
    }

    fun save(person: Person) {
        this.personDao.save(person)
    }

}