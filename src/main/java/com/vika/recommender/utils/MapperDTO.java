package com.vika.recommender.utils;

import org.modelmapper.ModelMapper;

public class MapperDTO {

    private static final ModelMapper MAPPER = new ModelMapper();

    public static <S, D> D mapTo(S obj, Class<D> destination) {
        return MAPPER. map(obj, destination);
    }

}
