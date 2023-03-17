package com.pedro.spring.integration;

import com.pedro.spring.domain.Person;
import com.pedro.spring.domain.Roles;
import com.pedro.spring.domain.UserAuthentication;
import com.pedro.spring.enums.RoleName;
import com.pedro.spring.repository.PersonRepository;
import com.pedro.spring.repository.RoleRepository;
import com.pedro.spring.repository.UserRepository;
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
class PersonControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    private void createUser() {
        Roles role = roleRepository.save(new Roles(null, RoleName.ROLE_ADMIN));
        UserAuthentication userAuthentication = new UserAuthentication
                (null, "test", "$2a$10$qPPE21npdx05OHn1ErI.HeMMWD.RtDqvt8q4U6Wh8idXebWGXCvVe", List.of(role));
        userRepository.save(userAuthentication);
    }


    @Test
    @DisplayName("List find all peoples on pageable when success full!")
    void list_FindAllPeoplesOnPageable_WhenSuccessFull() {
        createUser();
        Person personSaved = personRepository.save(PersonCreated.createPersonToBeSave());
        String personNameExpected = personSaved.getName();
        PageableResponse<Person> pagePerson = testRestTemplate.withBasicAuth("test", "test").exchange("/peoples", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Person>>() {
                }).getBody();
        Assertions.assertThat(pagePerson).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(pagePerson.toList().get(0).getName()).isEqualTo(personNameExpected);
        Assertions.assertThat(pagePerson.toList().get(0)).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("List find all peoples return list when success full!")
    void list_FindAllPeoplesList_WhenSuccessFull() {
        createUser();
        Person personSaved = personRepository.save(PersonCreated.createPersonToBeSave());
        String personNameExpected = personSaved.getName();
        List<Person> listPerson = testRestTemplate.withBasicAuth("test", "test").exchange("/peoples/findall", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Person>>() {
                }).getBody();
        Assertions.assertThat(listPerson).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(listPerson.get(0).getName()).isEqualTo(personNameExpected);
        Assertions.assertThat(listPerson.get(0)).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("List find person by id when success full!")
    void list_FindPersonById_WhenSuccessFull() {
        createUser();
        Person personSaved = personRepository.save(PersonCreated.createPersonToBeSave());
        String personNameExpected = personSaved.getName();
        Long personIdExpected = personSaved.getId();
        Person person = testRestTemplate.withBasicAuth("test", "test").exchange("/peoples/{id}", HttpMethod.GET, null,
                Person.class, personIdExpected).getBody();
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getName()).isEqualTo(personNameExpected);
        Assertions.assertThat(person).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("List find person by id is not exist return null when success full!")
    void list_FindPersonByIdNotExist_WhenSuccessFull() {
        createUser();
        ResponseEntity<Void> person = testRestTemplate.withBasicAuth("test", "test").exchange("/peoples/{id}", HttpMethod.GET, null,
                Void.class, 0L);
        Assertions.assertThat(person.getBody()).isNull();
    }

    @Test
    @DisplayName("List find all peoples on pageable by name when success full!")
    void list_FindAllPeoplesOnPageableByName_WhenSuccessFull() {
        createUser();
        Person personSaved = personRepository.save(PersonCreated.createPersonToBeSave());
        String personNameExpected = personSaved.getName();
        PageableResponse<Person> pagePerson = testRestTemplate.withBasicAuth("test", "test").exchange("/peoples/findallbyname?name={name}", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Person>>() {
                }, personNameExpected.replaceAll(" .*", "").toLowerCase()).getBody();
        Assertions.assertThat(pagePerson).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(pagePerson.toList().get(0)).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("List find all peoples of pageable by name when success full!")
    void list_FindAllPeoplesOfPageableByName_WhenSuccessFull() {
        Person personSaved = personRepository.save(PersonCreated.createPersonToBeSave());
        createUser();
        String personNameExpected = personSaved.getName();
        List<Person> pagePerson = testRestTemplate.withBasicAuth("test", "test").exchange("/peoples/findallbynameofpageable?name={name}", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Person>>() {
                }, personNameExpected.replaceAll(" .*", "").toLowerCase()).getBody();
        Assertions.assertThat(pagePerson).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(pagePerson.get(0)).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Post save person when success full! ")
    void post_SavePerson_WhenSuccessFull() {
        createUser();
        Person personSaved = testRestTemplate.withBasicAuth("test", "test").exchange("/peoples", HttpMethod.POST,
                new HttpEntity<>(PersonCreated.createPersonToBeSave()), Person.class).getBody();
        Assertions.assertThat(personSaved).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
        Assertions.assertThat(personSaved.getId()).isNotNull();

    }

    @Test
    @DisplayName("Delete person by id when success full!")
    void delete_PersonById_WhenSuccessFull() {
        createUser();
        Person personSaved = personRepository.save(PersonCreated.createPersonToBeSave());
        Long personIdExpected = personSaved.getId();
        Person person = testRestTemplate.withBasicAuth("test", "test").exchange("/peoples/{id}", HttpMethod.DELETE, null,
                Person.class, personIdExpected).getBody();
        Assertions.assertThat(person).isNull();
    }

    @Test
    @DisplayName("Replace save person when success full! ")
    void replace_SavePerson_WhenSuccessFull() {
        createUser();
        Person personSaved = personRepository.save(PersonCreated.createPersonToBeSave());
        ResponseEntity<Person> personReplace = testRestTemplate.withBasicAuth("test","test").exchange("/peoples", HttpMethod.PUT,
                new HttpEntity<>(PersonCreated.createPersonToBeReplace()), Person.class);
        Assertions.assertThat(personReplace.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        List<Person> listPerson = testRestTemplate.withBasicAuth("test", "test").exchange("/peoples/findall", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Person>>() {
                }).getBody();
        Assertions.assertThat(listPerson.get(0)).isEqualTo(PersonCreated.createPersonToBeReplace());

    }
}