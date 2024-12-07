package com.movinghouse.security.auth;

import com.movinghouse.security.model.Customer;
import com.movinghouse.security.model.Mover;
import com.movinghouse.security.repository.CustomerRepository;
import com.movinghouse.security.repository.MoverRepository;
import com.movinghouse.security.token.RefreshToken;
import com.movinghouse.security.token.RefreshTokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MoverRepository moverRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;
    private static final Logger logger = LogManager.getLogger(AuthService.class);

    public AuthResponse authenticateAndGenerateToken(String email, String password, String role){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        String token = generateToken(email, role);
        logger.info("[AuthController][authenticateAndGenerateToken] Access Token: " + token);
        logger.info("[AuthController][authenticateAndGenerateToken] Role: " + role);
        RefreshToken refreshToken = null;
        if(role.equals("CUSTOMER")){
            Customer customer = customerRepository.findByEmail(email).orElseThrow();
            logger.info("[AuthController][authenticateAndGenerateToken] Customer Name: " + customer.getName());
            refreshToken = refreshTokenService.createRefreshTokenForCustomer(customer);
        }else if(role.equals("MOVER")){
            Mover mover = moverRepository.findByEmail(email).orElseThrow();
            logger.info("[AuthController][authenticateAndGenerateToken] Customer Name: " + mover.getUsername());
            refreshToken = refreshTokenService.createRefreshTokenForMover(mover);
        }
        logger.info("[AuthController][authenticateAndGenerateToken] Refresh Token: " + refreshToken);
        return new AuthResponse(token, refreshToken.getToken());

    }
    public String generateToken(String email, String role){
        Instant now = Instant.now();
        long expiry = 3600L;
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(email)
                .claim("roles", role)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Mover saveMover(Mover mover){
        mover.setPassword(passwordEncoder.encode(mover.getPassword()));
        return moverRepository.save(mover);
    }

    public Customer saveCustomer(Customer customer){
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

}
