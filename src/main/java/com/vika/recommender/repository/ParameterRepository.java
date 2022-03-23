package com.vika.recommender.repository;

import com.vika.recommender.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    Parameter findByName(String name);

}
