package org.example.repository;

import org.example.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialRepo extends JpaRepository<UserCredential, Integer> {
    Optional<UserCredential> findByUsername(String username);
}
