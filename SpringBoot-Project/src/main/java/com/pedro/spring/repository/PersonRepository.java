package com.pedro.spring.repository;

import com.pedro.spring.domain.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {

    //query search by name on pageable
    @Query("SELECT p FROM Person p where LOWER(p.name) LIKE :name%")
    Page<Person> findAllByNameOnPageable(@Param("name") String name, Pageable pageable);
    //query search by name of pageable
    @Query("SELECT p FROM Person p where LOWER(p.name) LIKE :name%")
    List<Person> findAllByNameOfPageable(@Param("name") String name);
}
