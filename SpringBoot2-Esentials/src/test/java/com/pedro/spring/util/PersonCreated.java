package com.pedro.spring.util;

import com.pedro.spring.domain.Person;
import com.pedro.spring.request.PersonPostRequestBody;

public class PersonCreated {

    public static Person createPersonToBeSave(){
        return new Person(null,"person test");
    }

    public static Person createPersonToValid(){
        return new Person(1L,"person test");
    }


    public static Person createPersonToReplace(){
        return new Person(1L,"person update");
    }

}
