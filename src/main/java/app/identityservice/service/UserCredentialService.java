package app.identityservice.service;

import app.identityservice.entity.UserCredential;
import app.identityservice.repository.UserCredentialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCredentialService {

    private UserCredentialRepository userCredentialRepository;

    public UserCredential findByUsername(String username) {
        return userCredentialRepository.findByUsername(username).orElseThrow();
    }
}
