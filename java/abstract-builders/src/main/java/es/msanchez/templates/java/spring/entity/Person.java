package es.msanchez.templates.java.spring.entity;

import lombok.Data;

@Data
public class Person {

    private Long id;

    private Integer age;

    private String name;

    private Double doubleField;

    private Float floatField;

    private Boolean booleanField;

}
