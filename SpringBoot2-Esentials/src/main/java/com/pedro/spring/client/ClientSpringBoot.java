package com.pedro.spring.client;


import com.pedro.spring.domain.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
public class ClientSpringBoot {

    public static void main(String[] args) {
        //get
        log.info(new RestTemplate().exchange("http://localhost:8080/peoples/findall", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        }));

        // get by id
        log.info(new RestTemplate().exchange("http://localhost:8080/peoples/{id}", HttpMethod.GET, null, Person.class, 3));

        //get by name
        ResponseEntity<List<Person>> peoples = new RestTemplate().exchange("http://localhost:8080/peoples/findallbynameofpageable?name=pedro", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        });
        log.info(peoples);

        //post
        Person person = new Person(null, "Ana");
        log.info(new RestTemplate().exchange("http://localhost:8080/peoples", HttpMethod.POST, new HttpEntity<>(person), Person.class));

        //delete
        log.info(new RestTemplate().exchange("http://localhost:8080/peoples/{id}", HttpMethod.DELETE, null, Person.class, 3));

        //replace
        Person personReplace = new Person(1L, "Pedrão");
        log.info(new RestTemplate().exchange("http://localhost:8080/peoples", HttpMethod.PUT, new HttpEntity<>(personReplace), Person.class));

    }


}
