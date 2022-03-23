package com.vika.recommender.core;

import com.vika.recommender.model.Document;
import com.vika.recommender.model.Parameter;
import com.vika.recommender.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommenderImpl implements Recommender {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Document> findRelatedDocuments(CategoryQuery categoryQuery) {
        List<Parameter> parameters = new ArrayList<>(categoryRepository.findByName(categoryQuery.getCategoryName()).getParameters());
        return filterParameters(parameters, categoryQuery.getQueryParams());
    }

    private List<Document> filterParameters(List<Parameter> parameters, List<ParameterQuery> parameterQueries) {
        List<Document> resultDocs = new ArrayList<>();

        parameters
                .stream()
                .filter(param -> parameterQueries.stream().map(ParameterQuery::getParameterName).collect(Collectors.toList()).contains(param.getName()))
                .forEach(param -> {
                    resultDocs.addAll(param.getDocuments());
                    ParameterQuery relatedQuery = parameterQueries.stream().filter(pq -> pq.getParameterName().equals(param.getName())).findFirst().get();
                    resultDocs.addAll(filterParameters(param.getChildParams(), relatedQuery.getChildParams()));
                });

        return resultDocs;
    }

}
