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

    private static final Logger LOG = Logger.getLogger(String.valueOf(DbFunction.class));
    @Autowired
    DummyRepository dummyService;

    @Override
    public TemplateResponse apply(TemplateRequest request) {
        DynamoDbServiceImpl dbService = new DynamoDbServiceImpl();
        HashMap<String, String> attValues = new HashMap<>();
        attValues.put(":mId", "2302150437069940471");

        String filter = "evt_id = :mId";

        // int count = dbService.queryTable("ap-southeast-1", "evt_master", attValues, filter);
        dbService.updateField("ap-southeast-1", "evt_master", "2302150437069940471", "2302150437067269990", null, "done");

        // dbService.updateTableItem("ap-southeast-1", "al_test_sort", "table_id", "123", "status", "2");
        return new TemplateResponse();
    }
}
