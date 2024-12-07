package com.movinghouse.security.token;


import com.movinghouse.security.model.Customer;
import com.movinghouse.security.model.Mover;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant expiryDate;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Mover mover;
}