package com.vika.recommender.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.HashCodeExclude;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "category", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Set<Parameter> parameters;


    @Override
    public String toString() {
        return name;
    }
}
