package com.pedro.spring.repository;

import com.pedro.spring.domain.Person;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Log4j2
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    //crate entity
    private Person personCreated() {
        return new Person(null, "Pedro Test JPA Repository");
    }

    //get all
    @Test
    @DisplayName("List all peoples when success full!")
    void List_SelectAllPeoples_WhenSuccessFull() {
        Person personSaved = personRepository.save(personCreated());
        String personNameExpected = personSaved.getName();
        Assertions.assertThat(personRepository.findAll()).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(personRepository.findAll().get(0).getId()).isNotNull();
        Assertions.assertThat(personRepository.findAll().get(0).getName()).isEqualTo(personNameExpected);

    }

    @Test
    @DisplayName("Get person by id when success full!")
    void get_SelectPersonByID_WhenSuccessFull() {
        Person personSaved = personRepository.save(personCreated());
        String personNameExpected = personSaved.getName();
        Long personIdExpected = personSaved.getId();
        Assertions.assertThat(personRepository.findById(personIdExpected)).isNotNull().isNotEmpty();
        Assertions.assertThat(personRepository.findAll().get(0).getId());
        Assertions.assertThat(personRepository.findAll().get(0).getName()).isEqualTo(personNameExpected);

    }

    @Test
    @DisplayName("Get person by id  when not found!")
    void get_SelectPersonByID_WhenNotFound() {
        Assertions.assertThatCode(() -> personRepository.findById(1L)).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Get Select person by name when success full!")
    void get_SelectPersonByName_WhenSuccessFull() {
        Person personSaved = personRepository.save(personCreated());
        String personNameExpected = personSaved.getName().replaceAll(" .*", "").toLowerCase();
        Long personIdExpected = personSaved.getId();
        Assertions.assertThat(personRepository.findAllByNameOfPageable(personNameExpected.toString())).hasSize(1);
        Assertions.assertThat(personRepository.findAll().get(0).getId()).isEqualTo(personIdExpected);
        Assertions.assertThat(personRepository.findAll().get(0).getName()).isEqualTo(personSaved.getName());

    }

    @Test
    @DisplayName("Get Select person by name when not found!")
    void get_SelectPersonByName_WhenNotFound() {
        Assertions.assertThat(personRepository.findAllByNameOfPageable("name is not exist")).isEmpty();

    }

    @Test
    @DisplayName("Save post person when success full!")
    void Save_PostPerson_WhenSuccessFull() {
        Person personSaved = personRepository.save(personCreated());
        Assertions.assertThat(personSaved).isNotNull();
        Assertions.assertThat(personSaved.getId()).isNotNull();

    }

    @Test
    @DisplayName("Delete remove person when success full!")
    void Delete_RemovePerson_WhenSuccessFull() {
        Person personSaved = personRepository.save(personCreated());
        personRepository.deleteById(personSaved.getId());
        Assertions.assertThat(personRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("Replace update person when success full!")
    void Replace_UpdatePerson_WhenSuccessFull() {
        Person personSaved = personRepository.save(personCreated());
        personSaved.setName("Pedro Update");
        personRepository.save(personSaved);
        Assertions.assertThat(personRepository.findAll()).isNotEmpty();
    }




}