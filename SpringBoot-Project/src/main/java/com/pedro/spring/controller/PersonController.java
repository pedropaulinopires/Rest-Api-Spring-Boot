package com.pedro.spring.controller;

import com.pedro.spring.domain.Person;
import com.pedro.spring.request.PersonPostRequestBody;
import com.pedro.spring.request.PersonPutRequestBody;
import com.pedro.spring.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("peoples")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<Page<Person>> findAll(Pageable pageable) {
        return new ResponseEntity<>(personService.findAllOnPageable(pageable), HttpStatus.OK);
    }

    //test method
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("findall")
    public ResponseEntity<List<Person>> findAllOfPageable() {
        return new ResponseEntity<>(personService.findAllOfPageable(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("{id}")
    public ResponseEntity<Person> findById(@PathVariable long id) {
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("findallbyname")
    public ResponseEntity<Page<Person>> findAllByNameOnPageable(@RequestParam String name,Pageable pageable) {
        return new ResponseEntity<>(personService.findAllByNameOnPageable(name,pageable), HttpStatus.OK);
    }

    //test method
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("findallbynameofpageable")
    public ResponseEntity<List<Person>> findAllByNameOfPageable(@RequestParam String name) {
        return new ResponseEntity<>(personService.findAllByNameOfPageable(name), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Person> save(@RequestBody @Valid PersonPostRequestBody personPostRequestBody){
        return new ResponseEntity<>(personService.save(personPostRequestBody),HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        personService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid PersonPutRequestBody personPutRequestBody){
        personService.replace(personPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
