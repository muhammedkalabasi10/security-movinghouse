package com.movinghouse.security.token;

import com.movinghouse.security.model.Customer;
import com.movinghouse.security.model.Mover;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByCustomer(Customer customer);
    void deleteByMover(Mover mover);
}