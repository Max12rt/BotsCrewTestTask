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
    @Query(value = "SELECT * FROM lector WHERE LOWER(first_name) LIKE %:template% OR LOWER(last_name) LIKE %:template%", nativeQuery = true)
    List<Lector> globalSearch(@Param("template") String template);
}
