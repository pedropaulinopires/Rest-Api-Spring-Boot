package com.pedro.spring.repository;

import com.pedro.spring.domain.UserAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAuthentication,Long> {

    Optional<UserAuthentication> findByUsername(String username);
}
