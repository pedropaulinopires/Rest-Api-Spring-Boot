package com.pedro.spring.service;

import com.pedro.spring.domain.UserAuthentication;
import com.pedro.spring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthentication user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username "+username));
        return new User(user.getUsername(),user.getPassword(),user.isEnabled(),user.isAccountNonExpired()
        , user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());

    }
}
