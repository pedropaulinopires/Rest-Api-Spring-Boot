package com.pedro.spring.service;

import com.pedro.spring.domain.Person;
import com.pedro.spring.exception.BadRequestException;
import com.pedro.spring.repository.PersonRepository;
import com.pedro.spring.request.PersonPostRequestBody;
import com.pedro.spring.request.PersonPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    //inject dependency
    private final PersonRepository personRepository;

    /**
     *
     * method find all person on pageable
     *
     * */
    public Page<Person> findAllOnPageable(Pageable pageable){
        return personRepository.findAll(pageable);
    }

    /**
     *
     * method find all person of pageable
     *
     * */
    public List<Person> findAllOfPageable(){
        return personRepository.findAll();
    }

    /**
     *
     * method find person by id
     *
     * */
    public Person findById(long id){
        return personRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Person not found by id!")
        );
    }

    /**
     *
     * find person by name on pageable
     *
     * */
    public Page<Person> findAllByNameOnPageable(String name,Pageable pageable){
        return personRepository.findAllByNameOnPageable(name,pageable);
    }

    /**
     *
     * find person by name on pageable
     *
     * */
    public List<Person> findAllByNameOfPageable(String name){
        return personRepository.findAllByNameOfPageable(name);
    }

    /**
     *
     * method post save person
     *
     * */
    public Person save(PersonPostRequestBody personPostRequestBody){
        return personRepository.save(personPostRequestBody.build());
    }

    /**
     *
     * method put replace person
     *
     * */
    public void replace(PersonPutRequestBody personPutRequestBody){
        //search person
        findById(personPutRequestBody.getId());
        //save method repository
        personRepository.save(personPutRequestBody.build());
    }

    /**
     *
     * method delete person by id
     *
     * */
    public void deleteById(long id){
        personRepository.delete(findById(id));
    }
}
