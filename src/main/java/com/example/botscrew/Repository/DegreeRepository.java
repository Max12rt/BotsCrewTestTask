package com.example.botscrew.Repository;

import com.example.botscrew.Entity.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DegreeRepository extends JpaRepository<Degree, Long> {
    Optional<Degree> findByName (String name);
}
