package com.vika.recommender.core;

import lombok.Data;

import java.util.List;

@Data
public class ParameterQuery {

    private String parameterName;
    private List<ParameterQuery> childParams;

}
