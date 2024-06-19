package app.identityservice.service;

import app.identityservice.dto.UserCredentialDTO;
import app.identityservice.entity.UserCredential;
import app.identityservice.mapper.UserCredentialMapper;
import app.identityservice.repository.UserCredentialRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private UserCredentialRepository repository;

    private PasswordEncoder passwordEncoder;

    private JwtService jwtService;

    public UserCredentialDTO saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        try {
            UserCredential userSaved = repository.save(credential);
            return UserCredentialMapper.mapToUserCredentialDTO(userSaved);
        } catch (Exception e) {
            throw new RuntimeException("Duplicated username", e);
        }
    }

    public String generateToken(String username) {
        try {
            return jwtService.generateToken(username);
        } catch (Exception e) {
            throw new RuntimeException("User not found", e);
        }
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

}
