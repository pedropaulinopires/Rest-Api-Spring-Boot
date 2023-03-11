package com.pedro.spring.controller;

import com.pedro.spring.domain.Person;
import com.pedro.spring.request.PersonPostRequestBody;
import com.pedro.spring.request.PersonPutRequestBody;
import com.pedro.spring.service.PersonService;
import com.pedro.spring.util.PersonCreated;
import com.pedro.spring.util.PersonPostRequestBodyCreator;
import com.pedro.spring.util.PersonPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class PersonControllerTest {

    @InjectMocks
    //controller
    private PersonController personController;

    @Mock
    //dependency in controller;
    private PersonService personServiceMock;

    @BeforeEach
    void setUp() {
        //list all pageable
        PageImpl<Person> personPage = new PageImpl<>(List.of(PersonCreated.createPersonToValid()));
        BDDMockito.when(personServiceMock.findAllOnPageable(ArgumentMatchers.any())).thenReturn(personPage);
        ////////////////////

        //list all no pageable
        BDDMockito.when(personServiceMock.findAll()).thenReturn(List.of(PersonCreated.createPersonToValid()));
        /////////////////////

        //find by id
        BDDMockito.when(personServiceMock.findById(ArgumentMatchers.anyLong())).thenReturn(PersonCreated.createPersonToValid());
        /////////////////////

        //find person get by name on pageable
        BDDMockito.when(personServiceMock.findAllByNameOnPageable(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(personPage);
        ////////////////////

        //find person get by name of pageable
        BDDMockito.when(personServiceMock.findAllByName(ArgumentMatchers.anyString())).thenReturn(List.of(PersonCreated.createPersonToValid()));
        ///////////////////

        //post person
        BDDMockito.when(personServiceMock.save(ArgumentMatchers.any(PersonPostRequestBody.class))).thenReturn((PersonCreated.createPersonToValid()));
        //////////////////

        //replace person
        BDDMockito.doNothing().when(personServiceMock).replace(ArgumentMatchers.any(PersonPutRequestBody.class));
        /////////////////

        //delete person by id
        BDDMockito.doNothing().when(personServiceMock).delete(ArgumentMatchers.anyLong());
        ///////////////////


    }

    @Test
    @DisplayName("List find pageable lis of when success full!")
    void list_findOnPageablePersonListOf_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        Page<Person> personPage = personController.findAll(null).getBody();
        Assertions.assertThat(personPage).isNotNull();
        Assertions.assertThat(personPage.toList()).isNotEmpty();
        Assertions.assertThat(personPage.toList().get(0).getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("List all find person when success full!")
    void listAll_findAllPerson_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        List<Person> personList = personController.findAllNoPageable().getBody();
        Assertions.assertThat(personList).isNotNull();
        Assertions.assertThat(personList).isNotEmpty();
        Assertions.assertThat(personList.get(0).getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("Find person by id when success full!")
    void find_findByIdPerson_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        Person person = personController.findById(PersonCreated.createPersonToValid().getId()).getBody();
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("Find person by name on pageable when success full!")
    void find_findByNamePersonOnPageable_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        Page<Person> personPage = personController.findAllByNameOnPageable(personName, null).getBody();
        Assertions.assertThat(personPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(personPage.toList().get(0).getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("Find person by name of pageable when success full!")
    void find_findByNamePersonOfPageable_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        List<Person> personPage = personController.findAllByNameOfPageable(personName).getBody();
        Assertions.assertThat(personPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(personPage.get(0).getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("Find empty person by name when person not found!")
    void find_findByNameEmpty_WhenPersonNotFound() {
        BDDMockito.when(personServiceMock.findAllByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());
        List<Person> personList = personController.findAllByNameOfPageable("invalid").getBody();
        Assertions.assertThat(personList).isNotNull().isEmpty();
    }


    @Test
    @DisplayName("Save persist person  when success full!")
    void save_PersistPerson_WhenSuccessFull() {
        Person personSave = personController.save(PersonPostRequestBodyCreator.createPersonPostRequestBodyToBeSave()).getBody();

        Assertions.assertThat(personSave).isNotNull().isEqualTo(PersonCreated.createPersonToValid());
    }

    @Test
    @DisplayName("Replace update person  when success full!")
    void replace_UpdatePerson_WhenSuccessFull() {
        Assertions.assertThatCode(() -> personController.replace(PersonPutRequestBodyCreator.createPersonPutRequestBodyToBeSave())).doesNotThrowAnyException();

        //or

        ResponseEntity<Void> personReplace = personController.replace(PersonPutRequestBodyCreator.createPersonPutRequestBodyToBeSave());
        Assertions.assertThat(personReplace).isNotNull();
        Assertions.assertThat(personReplace.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("Delete removes person  when success full!")
    void Delete_RemovesPerson_WhenSuccessFull() {
        Assertions.assertThatCode(() -> personController.delete(1)).doesNotThrowAnyException();
        //or
        ResponseEntity<Void> personDelete = personController.delete(1);
        Assertions.assertThat(personDelete).isNotNull();
        Assertions.assertThat(personDelete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}