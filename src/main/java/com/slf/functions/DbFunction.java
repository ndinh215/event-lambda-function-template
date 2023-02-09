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
        HashMap<String, String> attValues = new HashMap<>();
        attValues.put(":mAddress", "HN");
        attValues.put(":mName", "Demo");

        String filter = "memberAddress = :mAddress AND memberName = :mName";

        int count = dbService.queryTable("us-west-2", "test-table", attValues, filter);
        dbService.updateField("us-west-2", "test-table", "123", null, "test-updated");

        return new TemplateResponse();
    }
}
