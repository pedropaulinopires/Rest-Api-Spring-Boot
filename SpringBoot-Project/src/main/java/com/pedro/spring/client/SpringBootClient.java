package com.pedro.spring.client;

import com.pedro.spring.domain.Person;
import com.pedro.spring.request.PersonPostRequestBody;
import com.pedro.spring.request.PersonPutRequestBody;
import com.pedro.spring.wrapper.PageableResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
public class SpringBootClient {

    public static void main(String[] args) {
//        //get on pageable
//        log.info(new RestTemplate().exchange("http://localhost:8080/peoples", HttpMethod.GET, null
//                , new ParameterizedTypeReference<PageableResponse<Person>>() {
//                }).getBody().stream().toList());
//
//        //get of pageable
//        log.info(new RestTemplate().exchange("http://localhost:8080/peoples/findall", HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Person>>() {
//                }));
//
//        //get id
//        log.info(new RestTemplate().exchange("http://localhost:8080/peoples/{id}",HttpMethod.GET,
//                null, Person.class,2L));
//
//
//        //get of pageable
//        log.info(new RestTemplate().exchange("http://localhost:8080/peoples/findallbyname?name={name}"
//                , HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Person>>() {
//                }, "pedro").getBody().stream().toList());
//
//        //get of pageable
//        log.info(new RestTemplate().exchange("http://localhost:8080/peoples/findallbynameofpageable?name={name}"
//                , HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
//                }, "pedro"));
//
//        //get of pageable
//        log.info(new RestTemplate().exchange("http://localhost:8080/peoples"
//                , HttpMethod.POST, new HttpEntity<>(new PersonPostRequestBody("Teste Pedro")), Person.class));

        //replace
//        log.info(new RestTemplate().exchange("http://localhost:8080/peoples"
//               , HttpMethod.PUT, new HttpEntity<>(new PersonPutRequestBody(4L,"Teste Pedro update")), Void.class));

        //delete by id
//        log.info(new RestTemplate().exchange("Http://localhost:8080/peoples/{id}",HttpMethod.DELETE,null,
//                Void.class,4L));

    }


}
