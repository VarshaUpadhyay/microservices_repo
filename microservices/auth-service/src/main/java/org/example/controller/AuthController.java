package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthenticationDto;
import org.example.entity.UserCredential;
import org.example.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody UserCredential userCredential) {
            authService.saveUser(userCredential);
            return "user registered";

    }

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public String generateToken(@RequestBody AuthenticationDto authDto){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword()));
        if (authentication.isAuthenticated()) {
        return authService.generateToken(authDto);
        }
        else
            return "user not found";
    }

    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public String validateToken(@RequestParam String token){
        try{
            authService.validateToken(token);
        }
        catch(Exception e){
            return e.getMessage();
        }
        return "Token is valid";
    }
}
