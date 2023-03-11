package com.pedro.spring.repository;

import com.pedro.spring.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE LOWER(p.name) LIKE :name% ")
    Page<Person> findAllByNameOnPageable(@Param("name") String name, Pageable pageable);

    @Query("SELECT p FROM Person p WHERE LOWER(p.name) LIKE :name% ")
    List<Person> findAllByNameofPageable(@Param("name") String name);
}
