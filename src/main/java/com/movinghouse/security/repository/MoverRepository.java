package com.movinghouse.security.repository;

import com.movinghouse.security.model.Mover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface MoverRepository extends JpaRepository<Mover, Long>, PagingAndSortingRepository<Mover, Long> {
    Optional<Mover> findByEmail(String email);
}
