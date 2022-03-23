package com.vika.recommender.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String ref;

    private String example;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "documents", fetch = FetchType.LAZY)
    private Set<Parameter> parameters;

    @Override
    public String toString() {
        return name;
    }
}
