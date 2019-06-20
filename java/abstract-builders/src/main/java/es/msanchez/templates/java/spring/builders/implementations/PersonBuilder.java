package es.msanchez.templates.java.spring.builders.implementations;

import es.msanchez.templates.java.spring.builders.generics.AbstractBuilder;
import es.msanchez.templates.java.spring.entity.Person;

public class PersonBuilder extends AbstractBuilder<PersonBuilder, Person> {

    public static PersonBuilder getInstance() {
        return new PersonBuilder();
    }

    @Override
    protected Person instantiate() {
        return new Person();
    }

    @Override
    protected PersonBuilder builder() {
        return this;
    }

    // Custom 'with' methods

    public PersonBuilder withAge(final Integer age) {
        super.with(p -> p.setAge(age));
        return this;
    }

    public PersonBuilder withName(final String name) {
        super.with(p -> p.setName(name));
        return this;
    }

}
