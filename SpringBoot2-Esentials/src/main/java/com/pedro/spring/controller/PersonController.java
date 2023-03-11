package com.pedro.spring.controller;

import com.pedro.spring.domain.Person;
import com.pedro.spring.request.PersonPostRequestBody;
import com.pedro.spring.request.PersonPutRequestBody;
import com.pedro.spring.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("peoples")
@RequiredArgsConstructor
@Log4j2
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<Page<Person>> findAll(Pageable pageable) {
        return new ResponseEntity<>(personService.findAllOnPageable(pageable), HttpStatus.OK);
    }

    @GetMapping("findall")
    public ResponseEntity<List<Person>> findAllNoPageable() {
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> findById(@PathVariable long id) {
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Person> findById(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return new ResponseEntity<>(personService.findById(id), HttpStatus.OK);
    }

    @GetMapping("findallbyname")
    public ResponseEntity<Page<Person>> findAllByNameOnPageable(@RequestParam String name, Pageable pageable) {
        return new ResponseEntity<>(personService.findAllByNameOnPageable(name, pageable), HttpStatus.OK);

    }

    @GetMapping("findallbynameofpageable")
    public ResponseEntity<List<Person>> findAllByNameOfPageable(@RequestParam String name) {
        return new ResponseEntity<>(personService.findAllByName(name), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Person> save(@Valid @RequestBody PersonPostRequestBody personPostRequestBody) {
        return new ResponseEntity<>(personService.save(personPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        personService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@Valid @RequestBody PersonPutRequestBody personPutRequestBody) {
        personService.replace(personPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
