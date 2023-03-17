package com.pedro.spring.controller;

import com.pedro.spring.domain.Person;
import com.pedro.spring.request.PersonPostRequestBody;
import com.pedro.spring.request.PersonPutRequestBody;
import com.pedro.spring.service.PersonService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(value = SpringExtension.class)
@AutoConfigureTestDatabase
class PersonControllerTest {

    //inject controller
    @InjectMocks
    private PersonController personController;

    //inject dependency use in controller
    @Mock
    private PersonService personServiceMock;

    @BeforeEach
    void setUp() {

        ///pageable person list
        PageImpl<Person> pagePerson = new PageImpl<>(List.of(PersonCreated.createPersonToBeValid()));
        ////////////////////////

        ///list person
        List<Person> listPerson = List.of(PersonCreated.createPersonToBeValid());
        ///////////////

        //get by id
        BDDMockito.when(personServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(PersonCreated.createPersonToBeValid());
        /////////////////////

        //get all on pageable
        BDDMockito.when(personServiceMock.findAllOnPageable(ArgumentMatchers.any())).thenReturn(pagePerson);
        /////////////////////

        //get all of pageable

        BDDMockito.when(personServiceMock.findAllOfPageable()).thenReturn(listPerson);
        /////////////////////

        //get all by name on pageable
        BDDMockito.when(personServiceMock.findAllByNameOnPageable(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(pagePerson);
        /////////////////////

        //get all by name of pageable
        BDDMockito.when(personServiceMock.findAllByNameOfPageable(ArgumentMatchers.anyString()))
                .thenReturn(listPerson);
        /////////////////////

        //post person
        BDDMockito.when(personServiceMock.save(ArgumentMatchers.any(PersonPostRequestBody.class)))
                .thenReturn(PersonCreated.createPersonToBeValid());
        /////////////

        //delete person by id
        BDDMockito.doNothing().when(personServiceMock).deleteById(ArgumentMatchers.anyLong());
        //////////////////////

        //replace person
        BDDMockito.doNothing().when(personServiceMock).replace(ArgumentMatchers.any(PersonPutRequestBody.class));


    }

    @Test
    @DisplayName("List find all peoples on pageable when success full!")
    void list_findAllPeoplesOnPageable_WhenSuccessFull() {
        Page<Person> pagePerson = personController.findAll(null).getBody();
        Assertions.assertThat(pagePerson).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(pagePerson.toList().get(0)).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("List find all peoples of pageable when success full!")
    void list_findAllPeoplesOfPageable_WhenSuccessFull() {
        List<Person> pagePerson = personController.findAllOfPageable().getBody();
        Assertions.assertThat(pagePerson).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(pagePerson.get(0)).isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Get Selected person by id when success full!")
    void get_SelectedPersonById_WhenSuccessFull() {
        Person personSaved = personController.findById(PersonCreated.createPersonToBeValid().getId()).getBody();
        Assertions.assertThat(personSaved).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Find Selected person by name on pageable when success full!")
    void find_SelectedPersonByNameOnPageable_WhenSuccessFull() {
        Page<Person> personPageName = personController
                .findAllByNameOnPageable(PersonCreated.createPersonToBeValid().getName(), null).getBody();
        Assertions.assertThat(personPageName.toList().get(0)).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Find Selected person by name of pageable when success full!")
    void find_SelectedPersonByNameOfPageable_WhenSuccessFull() {
        List<Person> personPageName = personController
                .findAllByNameOfPageable(PersonCreated.createPersonToBeValid().getName()).getBody();
        Assertions.assertThat(personPageName.get(0)).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Save post person when success full!")
    void save_PostPerson_WhenSuccessFull() {
        Person personSaved = personController
                .save(PersonPostBodyRequestCreated.createPersonPostBodyRequestToBeSave()).getBody();
        Assertions.assertThat(personSaved).isNotNull().isEqualTo(PersonCreated.createPersonToBeValid());
    }

    @Test
    @DisplayName("Put replace person when success full!")
    void put_ReplacePerson_WhenSuccessFull() {
        ResponseEntity<Void> personSaved = personController
                .replace(PersonPutBodyRequestCreated.createPersonPutBodyRequestToBeReplace());

        Assertions.assertThat(personSaved.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(personSaved).isNotNull();
    }

    @Test
    @DisplayName("Delete remove person by id when success full!")
    void delete_RemovePersonById_WhenSuccessFull() {
        ResponseEntity<Void> personRemove = personController
                .deleteById(PersonCreated.createPersonToBeValid().getId());

        Assertions.assertThat(personRemove.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Assertions.assertThat(personRemove).isNotNull();
    }
}