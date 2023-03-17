package com.pedro.spring.util;

import com.pedro.spring.request.PersonPostRequestBody;

public class PersonPostBodyRequestCreated {

    public static PersonPostRequestBody createPersonPostBodyRequestToBeSave() {
        return new PersonPostRequestBody("Pedro Test");
    }

}
