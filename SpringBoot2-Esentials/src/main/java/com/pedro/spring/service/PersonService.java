package com.pedro.spring.service;

import com.pedro.spring.domain.Person;
import com.pedro.spring.exception.BadRequestException;
import com.pedro.spring.repository.PersonRepository;
import com.pedro.spring.request.PersonPostRequestBody;
import com.pedro.spring.request.PersonPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Page<Person> findAllOnPageable(Pageable pageable){
        return personRepository.findAll(pageable);
    }

    public List<Person> findAll(){
        return personRepository.findAll();
    }


    public Page<Person> findAllByNameOnPageable(String name,Pageable pageable){
        return personRepository.findAllByNameOnPageable(name,pageable);
    }

    public Person findById(long id){
        return personRepository.findById(id).orElseThrow(
                () -> new BadRequestException("person not found by id")
        );
    }

    public List<Person> findAllByName(String name){
        return personRepository.findAllByNameofPageable(name);
    }

    public Person save(PersonPostRequestBody personPostRequestBody){
        return personRepository.save(personPostRequestBody.build());
    }

    public void delete(long id){
        findById(id);
        personRepository.deleteById(id);
    }

    public void replace(PersonPutRequestBody personPutRequestBody){
        findById(personPutRequestBody.getId());
        personRepository.save(personPutRequestBody.build());
    }


}
