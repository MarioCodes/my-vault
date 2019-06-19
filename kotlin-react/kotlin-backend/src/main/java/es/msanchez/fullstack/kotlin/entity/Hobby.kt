package es.msanchez.fullstack.kotlin.entity

import javax.persistence.*

@Entity
@Table(name = "hobby")
class Hobby(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var name: String
) {
    constructor() : this(name = "")
}