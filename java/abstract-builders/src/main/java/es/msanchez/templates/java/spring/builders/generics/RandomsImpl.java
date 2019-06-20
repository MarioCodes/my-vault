package es.msanchez.templates.java.spring.builders.generics;

import java.security.SecureRandom;
import java.util.Random;

public class RandomsImpl implements Randoms {

    private final static int DEFAULT_STRING_LENGTH = 50;

    protected Random randomGenerator() {
        return new SecureRandom();
    }

    @Override
    public Long randomLong() {
        final Random random = this.randomGenerator();
        return random.nextLong();
    }

    @Override
    public Long randomPositiveLong() {
        final Long randomLong = this.randomLong();
        return randomLong < 0L ? randomLong : randomLong * -1L;
    }

    @Override
    public Integer randomInteger() {
        final Random random = this.randomGenerator();
        return random.nextInt();
    }

    @Override
    public Integer randomPositiveInteger() {
        final Integer randomInt = this.randomInteger();
        return randomInt < 0 ? randomInt : randomInt * -1;
    }

    @Override
    public String randomAlphanumeric() {
        final String source = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return this.buildRandomString(source);
    }

    @Override
    public String randomNumeric() {
        final String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return this.buildRandomString(source);
    }

    private String buildRandomString(final String source) {
        final Random random = this.randomGenerator();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < DEFAULT_STRING_LENGTH; i++) {
            sb.append(source.charAt(random.nextInt(source.length())));
        }
        return sb.toString();
    }

}
