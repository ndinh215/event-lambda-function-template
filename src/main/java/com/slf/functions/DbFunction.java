package com.slf.functions;

import com.slf.core.services.repositories.DummyRepository;
import com.slf.models.TemplateRequest;
import com.slf.models.TemplateResponse;
import com.slf.services.DynamoDbServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.function.Function;
import java.util.logging.Logger;

@Component
public class DbFunction implements Function<TemplateRequest, TemplateResponse> {

    @Autowired
    DummyRepository dummyService;

    private static final Logger LOG = Logger.getLogger(String.valueOf(DbFunction.class));

    @Override
    public TemplateResponse apply(TemplateRequest request) {
        DynamoDbServiceImpl dbService = new DynamoDbServiceImpl();
        HashMap<String, String> keyValues = new HashMap<>();
        keyValues.put("mId", "123");

        HashMap<String, String> aliasList = new HashMap<>();
        aliasList.put("mId", "memberId");

        int count = dbService.queryTable("us-west-2", "test-table", keyValues, aliasList);

        return null;
    }
}
