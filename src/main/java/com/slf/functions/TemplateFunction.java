package com.slf.functions;

import com.slf.core.services.repositories.DummyRepository;
import com.slf.models.TemplateRequest;
import com.slf.models.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.logging.Logger;

@Component
public class TemplateFunction implements Function<TemplateRequest, TemplateResponse> {

    @Autowired
    DummyRepository dummyService;

    private static final Logger LOG = Logger.getLogger(String.valueOf(TemplateFunction.class));

    @Override
    public TemplateResponse apply(TemplateRequest request) {
        TemplateResponse response = new TemplateResponse();
        response.setResult(dummyService.get());

        LOG.info("[INFO] " + response.getResult());

        return response;
    }
}
