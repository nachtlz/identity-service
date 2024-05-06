package app.identityservice.controller;

import app.identityservice.dto.AuthRequest;
import app.identityservice.dto.TokenDTO;
import app.identityservice.entity.UserCredential;
import app.identityservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserCredential user) {
        try {
            authService.saveUser(user);
            return ResponseEntity.ok("Created");
        } catch (Exception e) {
            return new ResponseEntity<>("Duplicated username", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/token")
    public ResponseEntity<TokenDTO> getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()) {
            TokenDTO tokenDTO = new TokenDTO(authService.generateToken(authRequest.getUsername()));
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
