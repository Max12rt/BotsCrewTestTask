package com.example.botscrew.Repository;

import com.example.botscrew.Entity.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectorRepository extends JpaRepository<Lector,Long> {
    List<Lector> findByDepartmentsName(String departmentName);
    List<Lector> findByFirstNameAndLastName(String firstname, String lastname);
    @Query("SELECT l FROM Lector l WHERE LOWER(l.firstName) LIKE :template OR LOWER(l.lastName) LIKE :template")
    List<Lector> globalSearch(@Param("template") String template);
}
