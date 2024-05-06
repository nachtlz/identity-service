package app.identityservice.service;

import app.identityservice.config.CustomUserDetails;
import app.identityservice.entity.UserCredential;
import app.identityservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredential = repository.findByUsername(username);
        return userCredential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + username));
    }
}
