package es.msanchez.fullstack.kotlin.entity

import javax.persistence.*

@Entity
@Table(name = "course")
class Course(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var username: String,
        var description: String) {

    constructor() : this(username = "",
            description = "")

    constructor(us: String,
                de: String) : this(username = us,
            description = de)
}