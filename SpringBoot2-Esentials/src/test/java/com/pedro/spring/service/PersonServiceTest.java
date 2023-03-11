package com.pedro.spring.service;

import com.pedro.spring.domain.Person;
import com.pedro.spring.exception.BadRequestException;
import com.pedro.spring.repository.PersonRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {
    @InjectMocks
    //controller
    private PersonService personService;

    @Mock
    //dependency in controller;
    private PersonRepository personRepositoryMock;

    @BeforeEach
    void setUp() {
        //list all pageable
        PageImpl<Person> personPage = new PageImpl<>(List.of(PersonCreated.createPersonToValid()));
        BDDMockito.when(personRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(personPage);
        ////////////////////

        //list all no pageable
        BDDMockito.when(personRepositoryMock.findAll()).thenReturn(List.of(PersonCreated.createPersonToValid()));
        /////////////////////

        //find by id
        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(PersonCreated.createPersonToValid()));
        /////////////////////

        //find person get by name on pageable
        BDDMockito.when(personRepositoryMock.findAllByNameOnPageable(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(personPage);
        ////////////////////

        //find person get by name of pageable
        BDDMockito.when(personRepositoryMock.findAllByNameofPageable(ArgumentMatchers.anyString())).thenReturn(List.of(PersonCreated.createPersonToValid()));
        ///////////////////

        //post person
        //////////////////
        BDDMockito.when(personRepositoryMock.save(ArgumentMatchers.any(Person.class))).thenReturn(PersonCreated.createPersonToValid());

        //replace person

        /////////////////

        //delete person by id
        BDDMockito.doNothing().when(personRepositoryMock).delete(ArgumentMatchers.any(Person.class));
        ///////////////////


    }

    @Test
    @DisplayName("findAllOnPageable find pageable lis of when success full!")
    void findAllOnPageable_findOnPageablePersonListOf_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        Page<Person> personPage = personService.findAllOnPageable(PageRequest.of(0, 1));
        Assertions.assertThat(personPage).isNotNull();
        Assertions.assertThat(personPage.toList()).isNotEmpty();
        Assertions.assertThat(personPage.toList().get(0).getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("findAll all find person when success full!")
    void findAll_findAllPerson_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        List<Person> personList = personService.findAll();
        Assertions.assertThat(personList).isNotNull();
        Assertions.assertThat(personList).isNotEmpty();
        Assertions.assertThat(personList.get(0).getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("findById person by id when success full!")
    void findById_findByIdPerson_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        Person person = personService.findById(PersonCreated.createPersonToValid().getId());
        Assertions.assertThat(person).isNotNull();
        Assertions.assertThat(person.getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("findById throws BadRequestException when  person not found  by id!")
    void findById_findByIdPersonThrowsBadRequestException_WhenPersonNotFound() {
        BDDMockito.when(personRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> personService.findById(1)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("findAllByNameOnPageable person by name on pageable when success full!")
    void findAllByNameOnPageable_findByNamePersonOnPageable_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        Page<Person> personPage = personService.findAllByNameOnPageable(personName, null);
        Assertions.assertThat(personPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(personPage.toList().get(0).getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("findAllByName person by name of pageable when success full!")
    void findAllByName_findByNamePersonOfPageable_WhenSuccessFull() {
        String personName = PersonCreated.createPersonToValid().getName();
        List<Person> personPage = personService.findAllByName(personName);
        Assertions.assertThat(personPage).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(personPage.get(0).getName()).isEqualTo(personName);
    }

    @Test
    @DisplayName("findAllByName empty person by name when person not found!")
    void findAllByName_findByNameEmpty_WhenPersonNotFound() {
        BDDMockito.when(personService.findAllByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());
        List<Person> personList = personService.findAllByName("invalid");
        Assertions.assertThat(personList).isNotNull().isEmpty();
    }


    @Test
    @DisplayName("Save persist person  when success full!")
    void save_PersistPerson_WhenSuccessFull() {
        Person personSave = personService.save(PersonPostRequestBodyCreator.createPersonPostRequestBodyToBeSave());

        Assertions.assertThat(personSave).isNotNull().isEqualTo(PersonCreated.createPersonToValid());
    }

    @Test
    @DisplayName("Replace update person  when success full!")
    void replace_UpdatePerson_WhenSuccessFull() {
        Assertions.assertThatCode(() -> personService.replace(PersonPutRequestBodyCreator.createPersonPutRequestBodyToBeSave())).doesNotThrowAnyException();


    }

    @Test
    @DisplayName("Delete removes person  when success full!")
    void Delete_RemovesPerson_WhenSuccessFull() {
        Assertions.assertThatCode(() -> personService.delete(1)).doesNotThrowAnyException();

    }

}