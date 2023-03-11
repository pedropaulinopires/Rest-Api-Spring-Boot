package com.pedro.spring.repository;

import com.pedro.spring.domain.Person;
import com.pedro.spring.util.PersonCreated;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DisplayName("Save persist person when success full!")
    void save_PersistPerson_WhenSuccessFull() {
        Person person = PersonCreated.createPersonToBeSave();
        Person save = personRepository.save(person);
        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getId()).isNotNull();
        Assertions.assertThat(save.getName()).isEqualTo(person.getName());
    }

    @Test
    @DisplayName("Find person by id when success full!")
    void find_findPersonById_WhenSuccessFull() {
        Person person = PersonCreated.createPersonToBeSave();
        Person save = personRepository.save(person);
        Long id = save.getId();
        Optional<Person> personId = personRepository.findById(id);
        Assertions.assertThat(personId).isNotEmpty();
        Assertions.assertThat(personId.get().getName()).isEqualTo(person.getName());

    }

    @Test
    @DisplayName("Find person by name when success full!")
    void find_findPersonByName_WhenSuccessFull() {
        Person person = PersonCreated.createPersonToBeSave();
        Person save = personRepository.save(person);
        String name = save.getName();
        List<Person> peoples = personRepository.findAllByNameofPageable(person.getName());
        Assertions.assertThat(peoples).isNotEmpty();
        Assertions.assertThat(peoples.get(0).getName()).isEqualTo(person.getName());

    }

    @Test
    @DisplayName("Replace update person when success full!")
    void replace_UpdatePerson_WhenSuccessFull() {
        Person person = PersonCreated.createPersonToBeSave();
        Person save = personRepository.save(person);
        Long id = save.getId();
        person.setName("Update");
        person.setId(id);
        Person personUpdate = personRepository.save(person);
        Assertions.assertThat(personUpdate).isNotNull();
        Assertions.assertThat(personUpdate.getName()).isEqualTo(person.getName());
        Assertions.assertThat(personUpdate.getId()).isEqualTo(person.getId());

    }

    @Test
    @DisplayName("Delete person by id when success full!")
    void delete_DeletePersonById_WhenSuccessFull() {
        Person person = PersonCreated.createPersonToBeSave();
        Person save = personRepository.save(person);
        Long id = save.getId();
        personRepository.deleteById(id);
        Optional<Person> personFind = personRepository.findById(id);
        Assertions.assertThat(personFind).isEmpty();

    }

    @Test
    @DisplayName("list empty lis person when success full!")
    void list_ListPersonIsEmpty_WhenSuccessFull() {
        List<Person> peoples = personRepository.findAll();
        Assertions.assertThat(peoples).isEmpty();

    }

    @Test
    @DisplayName("Throw exception ConstraintViolationException when person is null!")
    void find_ConstraintViolationException_WhenPersonNotFound() {
        Person person = new Person();
        Assertions.assertThatThrownBy(() -> personRepository.save(person)).isInstanceOf(ConstraintViolationException.class);

    }


}