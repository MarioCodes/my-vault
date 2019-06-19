package es.msanchez.fullstack.kotlin.rest

import es.msanchez.fullstack.kotlin.entity.Course
import es.msanchez.fullstack.kotlin.service.CourseService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("courses")
class CourseRestResource(private val courseService: CourseService) {

    init {
        this.courseService.initializeTestData()
    }

    @GetMapping
    fun getAllCourses(): MutableIterable<Course> {
        return this.courseService.findAll()
    }

    @GetMapping("instructors/{username}")
    fun getCoursesByUsername(@PathVariable username: String): MutableIterable<Course> {
        return this.courseService.findAllByUsername(username)
    }

}