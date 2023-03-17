package com.pedro.spring.service;

import com.pedro.spring.controller.PersonController;
import com.pedro.spring.domain.Person;
import com.pedro.spring.repository.PersonRepository;
import com.pedro.spring.request.PersonPostRequestBody;
import com.pedro.spring.request.PersonPutRequestBody;
import com.pedro.spring.util.PersonCreated;
import com.pedro.spring.util.PersonPostBodyRequestCreated;
import com.pedro.spring.util.PersonPutBodyRequestCreated;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(value = SpringExtension.class)
@AutoConfigureTestDatabase
class PersonServiceTest {


    //inject controller
    @InjectMocks
    private PersonService personService;

    //inject dependency use in controller
    @Mock
    private PersonRepository personRepositoryMock;

    @BeforeEach
    void setUp() {

        ///pageable person list
        PageImpl<Person> pagePerson = new PageImpl<>(List.of(PersonCreated.createPersonToBeValid()));
        ////////////////////////

        ///list person
        List<Person> listPerson = List.of(PersonCreated.createPersonToBeValid());
        ///////////////

        //get by id
        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(PersonCreated.createPersonToBeValid()));
        /////////////////////

        //get all on pageable
        BDDMockito.when(personRepositoryMock.findAll(Pageable.unpaged())).thenReturn(pagePerson);
        /////////////////////

        //get all of pageable

        BDDMockito.when(personRepositoryMock.findAll()).thenReturn(listPerson);
        /////////////////////

        //get all by name on pageable
        BDDMockito.when(personRepositoryMock.findAllByNameOnPageable(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(pagePerson);
        /////////////////////

        //get all by name of pageable
        BDDMockito.when(personRepositoryMock.findAllByNameOfPageable(ArgumentMatchers.anyString()))
                .thenReturn(listPerson);
        /////////////////////

        //post person
        BDDMockito.when(personRepositoryMock.save(ArgumentMatchers.any(Person.class)))
                .thenReturn(PersonCreated.createPersonToBeValid());
        /////////////

        //delete person by id
        BDDMockito.doNothing().when(personRepositoryMock).deleteById(ArgumentMatchers.anyLong());
        //////////////////////

    }

    @Test
    @DisplayName("List find all peoples on pageable when success full!")
    void list_findAllPeoplesOnPageable_WhenSuccessFull() {
        personService.save(PersonPostBodyRequestCreated.createPersonPostBodyRequestToBeSave());
        Page<Person> pagePerson = personService.findAllOnPageable(Pageable.unpaged());
        Assertions.assertThat(pagePerson).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(pagePerson.toList().get(0)).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("List find all peoples of pageable when success full!")
    void list_findAllPeoplesOfPageable_WhenSuccessFull() {
        List<Person> pagePerson = personService.findAllOfPageable();
        Assertions.assertThat(pagePerson).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(pagePerson.get(0)).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Get Selected person by id when success full!")
    void get_SelectedPersonById_WhenSuccessFull() {
        Person personSaved = personService.findById(PersonCreated.createPersonToBeValid().getId());
        Assertions.assertThat(personSaved).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Find Selected person by name on pageable when success full!")
    void find_SelectedPersonByNameOnPageable_WhenSuccessFull() {
        Page<Person> personPageName = personService
                .findAllByNameOnPageable(PersonCreated.createPersonToBeValid().getName(), null);
        Assertions.assertThat(personPageName.toList().get(0)).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Find Selected person by name of pageable when success full!")
    void find_SelectedPersonByNameOfPageable_WhenSuccessFull() {
        List<Person> personPageName = personService
                .findAllByNameOfPageable(PersonCreated.createPersonToBeValid().getName());
        Assertions.assertThat(personPageName.get(0)).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Save post person when success full!")
    void save_PostPerson_WhenSuccessFull() {
        Person personSaved = personService
                .save(PersonPostBodyRequestCreated.createPersonPostBodyRequestToBeSave());
        Assertions.assertThat(personSaved).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Put replace person when success full!")
    void put_ReplacePerson_WhenSuccessFull() {
        Assertions.assertThatCode( () -> personService.replace(PersonPutBodyRequestCreated
                .createPersonPutBodyRequestToBeReplace())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Delete remove person by id when success full!")
    void delete_RemovePersonById_WhenSuccessFull() {
        Assertions.assertThatCode(() -> personService.deleteById(PersonCreated.createPersonToBeValid().getId())).doesNotThrowAnyException();
    }

}