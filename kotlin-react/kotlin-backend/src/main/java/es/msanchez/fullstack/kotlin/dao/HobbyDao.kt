package es.msanchez.fullstack.kotlin.dao

import es.msanchez.fullstack.kotlin.entity.Hobby
import org.springframework.stereotype.Repository

@Repository
interface HobbyDao : RawDao<Hobby>