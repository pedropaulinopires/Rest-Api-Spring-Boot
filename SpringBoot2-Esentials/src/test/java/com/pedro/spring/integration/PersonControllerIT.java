package com.pedro.spring.integration;

import com.pedro.spring.domain.Person;
import com.pedro.spring.repository.PersonRepository;
import com.pedro.spring.util.PersonCreated;
import com.pedro.spring.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("List find pageable lis of when success full!")
    void list_findOnPageablePersonListOf_WhenSuccessFull() {
        Person personSave = personRepository.save(PersonCreated.createPersonToValid());
        String personName = personSave.getName();
        PageableResponse<Person> personPage = testRestTemplate.exchange("/peoples", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Person>>() {
        }).getBody();
        Assertions.assertThat(personPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(personPage.toList().get(0).getName()).isEqualTo(personName);

    }

    @Test
    @DisplayName("List all find person when success full!")
    void listAll_findAllPerson_WhenSuccessFull() {
        Person personSave = personRepository.save(PersonCreated.createPersonToValid());
        String personName = PersonCreated.createPersonToValid().getName();
        List<Person> personList = testRestTemplate.exchange("/peoples/findall", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        }).getBody();
        Assertions.assertThat(personList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(personList.get(0).getName()).isEqualTo(personName);

    }

    @Test
    @DisplayName("Find person by id when success full!")
    void find_findByIdPerson_WhenSuccessFull() {
        Person personSave = personRepository.save(PersonCreated.createPersonToValid());
        String personName = personSave.getName();
        Person person = testRestTemplate.exchange("/peoples/{id}", HttpMethod.GET, null, Person.class, 1).getBody();
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getName()).isEqualTo(personName);

    }

    @Test
    @DisplayName("Find person by name on pageable when success full!")
    void find_findByNamePersonOnPageable_WhenSuccessFull() {
        Person personSave = personRepository.save(PersonCreated.createPersonToValid());
        String personName = personSave.getName();
        PageableResponse<Person> personPage = testRestTemplate.exchange("/peoples/findallbyname?name={name}", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Person>>() {
        }, personName).getBody();
        Assertions.assertThat(personPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(personPage.toList().get(0).getName()).isEqualTo(personName);

    }

    @Test
    @DisplayName("Find person by name of pageable when success full!")
    void find_findByNamePersonOfPageable_WhenSuccessFull() {
        Person personSave = personRepository.save(PersonCreated.createPersonToValid());
        String personName = PersonCreated.createPersonToValid().getName();
        List<Person> personList = testRestTemplate.exchange("/peoples/findallbynameofpageable?name={name}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        }, personName).getBody();
        Assertions.assertThat(personList).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(personList.get(0).getName()).isEqualTo(personName);

    }

    @Test
    @DisplayName("Find empty person by name when person not found!")
    void find_findByNameEmpty_WhenPersonNotFound() {
        List<Person> notExist = testRestTemplate.exchange("/peoples/findallbynameofpageable?name={name}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        }, "not exist").getBody();
        Assertions.assertThat(notExist).isNotNull().isEmpty();

    }

    @Test
    @DisplayName("Save persist person  when success full!")
    void save_PersistPerson_WhenSuccessFull() {
        ResponseEntity<Person> personSave = testRestTemplate.postForEntity("/peoples", PersonCreated.createPersonToBeSave(), Person.class);
        Assertions.assertThat(personSave.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(personSave.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("Replace update person  when success full!")
    void replace_UpdatePerson_WhenSuccessFull() {
        Person personSave = personRepository.save(PersonCreated.createPersonToBeSave());
        ResponseEntity<Void> personReplace = testRestTemplate.exchange("/peoples", HttpMethod.PUT, new HttpEntity<>(PersonCreated.createPersonToReplace()), Void.class);
        Assertions.assertThat(personReplace).isNotNull();
        Assertions.assertThat(personReplace.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Delete removes person  when success full!")
    void Delete_RemovesPerson_WhenSuccessFull() {
        Person personSave = personRepository.save(PersonCreated.createPersonToBeSave());
        ResponseEntity<Void> personDelete = testRestTemplate.exchange("/peoples/{id}", HttpMethod.DELETE, null, Void.class, personSave.getId());
        Assertions.assertThat(personDelete).isNotNull();
        Assertions.assertThat(personDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        List<Person> personList = personRepository.findAll();
        Assertions.assertThat(personList).isNotNull().isEmpty();
    }

}

