package com.vika.recommender.core;

import lombok.Data;
import java.util.List;

@Data
public class CategoryQuery {

    private String categoryName;
    private List<ParameterQuery> queryParams;

}
