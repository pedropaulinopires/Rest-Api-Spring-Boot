package com.pedro.spring.util;

import com.pedro.spring.domain.Person;

public class PersonCreated {

    public static Person createPersonToBeSave() {
        return new Person(null, "Pedro Test");
    }

    public static Person createPersonToBeValid() {
        return new Person(1L, "Pedro Test");
    }

    public static Person createPersonToBeReplace() {
        return new Person(1L, "Pedro Update");
    }
}
