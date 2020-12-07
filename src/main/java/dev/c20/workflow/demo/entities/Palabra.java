package dev.c20.workflow.demo.entities;


import dev.c20.workflow.WorkflowApplication;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name= "palabras")
public class Palabra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="palabra")
    private String word;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Palabra dbFile = (Palabra) o;
        return id.equals(dbFile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public Palabra setId(Long id) {
        this.id = id;
        return this;
    }

    public String getWord() {
        return word;
    }

    public Palabra setWord(String word) {
        this.word = word;
        return this;
    }
}
