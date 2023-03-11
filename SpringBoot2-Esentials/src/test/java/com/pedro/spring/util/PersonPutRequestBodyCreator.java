package com.pedro.spring.util;

import com.pedro.spring.request.PersonPutRequestBody;

public class PersonPutRequestBodyCreator {

    public static PersonPutRequestBody createPersonPutRequestBodyToBeSave(){
        return PersonPutRequestBody.builder()
                .id(PersonCreated.createPersonToReplace().getId())
                .name(PersonCreated.createPersonToReplace().getName())
                .build();
    }


}
