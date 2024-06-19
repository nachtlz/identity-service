package app.identityservice.mapper;

import app.identityservice.dto.AuthRequest;
import app.identityservice.dto.UserCredentialDTO;
import app.identityservice.entity.UserCredential;

public class UserCredentialMapper {

    public static UserCredentialDTO mapToUserCredentialDTO(UserCredential userCredential) {
        return new UserCredentialDTO(userCredential.getId(), userCredential.getUsername());
    }
}
