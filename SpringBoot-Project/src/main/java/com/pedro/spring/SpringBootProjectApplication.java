package com.pedro.spring;

import com.pedro.spring.domain.Roles;
import com.pedro.spring.enums.RoleName;
import com.pedro.spring.repository.RoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootProjectApplication {

	public static void main(String[] args) {SpringApplication.run(SpringBootProjectApplication.class, args);
	}
}
