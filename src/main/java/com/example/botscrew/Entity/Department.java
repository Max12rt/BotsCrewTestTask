package com.example.botscrew.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne
    private Lector head;

    @ManyToMany
    @JoinTable(
            name = "department_lectors",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "lector_id")
    )
    private Set<Lector> lectors = new HashSet<>();


    public Department() {}

    public Department(Long id, String name, Lector head, Set<Lector> lectors) {
        this.id = id;
        this.name = name;
        this.head = head;
        this.lectors = lectors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Lector getHead() {
        return head;
    }

    public Set<Lector> getLectors() {
        return lectors;
    }

    public void setHead(Lector head) {
        this.head = head;
    }

    public void setLectors(Set<Lector> lectors) {
        this.lectors = lectors;
    }

}
