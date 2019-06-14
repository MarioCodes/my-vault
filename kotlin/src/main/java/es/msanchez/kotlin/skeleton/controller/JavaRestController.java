package es.msanchez.kotlin.skeleton.controller;

import es.msanchez.kotlin.playzone.basic.syntax.BasicSyntax;
import es.msanchez.kotlin.skeleton.validator.JavaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JavaRestController {

    private final JavaValidator javaValidator;

    @Autowired
    public JavaRestController(final JavaValidator javaValidator) {
        this.javaValidator = javaValidator;
    }

    @RequestMapping("/java")
    public String index() {
        return this.javaValidator.validate();
    }

    @RequestMapping("/kotlin/test")
    public String classes() {
        final BasicSyntax syntax = new BasicSyntax();
        syntax.stringTemplates();
        return "";
    }

}
