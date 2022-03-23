package com.vika.recommender.core;

import com.vika.recommender.model.Document;

import java.util.List;

public interface Recommender {

    List<Document> findRelatedDocuments(CategoryQuery categoryQuery);

}
