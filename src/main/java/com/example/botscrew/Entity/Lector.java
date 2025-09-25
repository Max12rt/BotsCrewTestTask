package com.example.botscrew.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToOne
    private Degree degree;

    private Double salary;


    @ManyToMany
            (mappedBy = "lectors")
    private Set<Department> departments = new HashSet<>();

    public Lector() {}

    public Lector(Long id, String firstName, String lastName, Degree degree, Double salary, Set<Department> departments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.salary = salary;
        this.departments = departments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Degree getDegree() {
        return degree;
    }

    public Double getSalary() {
        return salary;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
