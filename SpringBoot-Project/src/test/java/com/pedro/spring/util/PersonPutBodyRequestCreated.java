package com.pedro.spring.util;

import com.pedro.spring.request.PersonPutRequestBody;

public class PersonPutBodyRequestCreated {

    public static PersonPutRequestBody createPersonPutBodyRequestToBeReplace() {
        return new PersonPutRequestBody(1L, "Pedro Update");
    }

}
