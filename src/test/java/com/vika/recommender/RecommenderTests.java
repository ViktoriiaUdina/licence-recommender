package com.vika.recommender;

import com.vika.recommender.core.Recommender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecommenderTests {

    @Autowired
    private Recommender recommender;

    @Test
    public void testFindDocs() {

    }
}
