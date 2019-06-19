package es.msanchez.fullstack.kotlin.service

import es.msanchez.fullstack.kotlin.dao.CourseDao
import es.msanchez.fullstack.kotlin.entity.Course
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.BDDAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CourseServiceTest {

    @MockK
    lateinit var dao: CourseDao

    @InjectMockKs
    lateinit var service: CourseService

    @Test
    internal fun testFindAllByUsername() {
        // Given
        val username = "mario"

        val foundCourses = mutableListOf<Course>()
        every { dao.findAllByUsername(username) } returns foundCourses

        // When
        val result: MutableIterable<Course> = this.service.findAllByUsername(username)

        // Then

        verify { dao.findAllByUsername(username) }

        assertThat(result).isSameAs(foundCourses)
    }

    @Test
    internal fun testFindAll() {
        // Given
        val foundCourses = mutableListOf<Course>()
        every { dao.findAll() } returns foundCourses

        // When
        val result: MutableIterable<Course> = this.service.findAll()

        // Then
        BDDAssertions.assertThat(result).isSameAs(foundCourses)
        verify { dao.findAll() }
    }

}