package es.msanchez.fullstack.kotlin.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = ["es.msanchez.fullstack.kotlin.**.*"])
class SpringConfig