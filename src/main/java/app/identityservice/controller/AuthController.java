package app.identityservice.controller;

import app.identityservice.dto.AuthRequest;
import app.identityservice.dto.PersonDTO;
import app.identityservice.dto.TokenDTO;
import app.identityservice.entity.UserCredential;
import app.identityservice.service.AuthService;
import app.identityservice.service.UserCredentialService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    private AuthenticationManager authenticationManager;

    private UserCredentialService userCredentialService;

    @PostMapping("/register")
    public ResponseEntity<PersonDTO> addNewUser(@RequestBody UserCredential user) {
        try {
            UserCredential userSaved = authService.saveUser(user);
            PersonDTO personDTO = new PersonDTO(userSaved.getId(), userSaved.getUsername());
            return ResponseEntity.ok(personDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(new PersonDTO(0, ""), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDTO> getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()) {
            UserCredential userCredential = userCredentialService.findByUsername(authRequest.getUsername());
            TokenDTO tokenDTO = new TokenDTO(authService.generateToken(authRequest.getUsername()), userCredential.getId());
            return new ResponseEntity<>(tokenDTO, HttpStatus.OK);
        } else {
            throw new RuntimeException("Invalid access");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return ResponseEntity.ok("Valid token");
    }
}
