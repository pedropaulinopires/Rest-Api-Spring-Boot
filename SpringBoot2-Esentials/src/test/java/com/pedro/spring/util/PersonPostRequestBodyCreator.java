package com.pedro.spring.util;

import com.pedro.spring.request.PersonPostRequestBody;

public class PersonPostRequestBodyCreator {

    public static PersonPostRequestBody createPersonPostRequestBodyToBeSave(){
        return PersonPostRequestBody.builder()
                .name(PersonCreated.createPersonToValid().getName())
                .build();
    }


}
