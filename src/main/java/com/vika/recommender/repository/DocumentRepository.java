package com.vika.recommender.repository;

import com.vika.recommender.model.Document;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Document findByName(String name);

    @Query("select distinct d from Document d join d.parameters parameters where parameters.id in :ids")
    List<Document> findByParameterIds(@Param("ids") List<Long> parameterIds);
}
