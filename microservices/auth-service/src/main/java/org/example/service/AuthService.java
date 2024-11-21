package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthenticationDto;
import org.example.entity.UserCredential;
import org.example.repository.UserCredentialRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialRepo userCredentialRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String saveUser(UserCredential userCredential) {
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepo.save(userCredential);
        return "User Saved Successfully";
    }

    public String generateToken(AuthenticationDto authenticationDto) {
        return jwtService.generateJwtToken(authenticationDto);
    }

    public void validateToken(String token) {
        jwtService.validateJwtToken(token);
    }
}
