package com.slf.functions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slf.handlers.FunctionApplication;
import com.slf.models.TemplateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FunctionApplication.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
@AutoConfigureJsonTesters
public class FunctionTest {

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<TemplateRequest> json;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void testFunction() throws Exception {
        TemplateRequest request = new TemplateRequest();
        request.setData("test");

        mockMvc.perform(post("/handle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json.write(request).getJson()))
                .andExpect(status().isOk())
                .andReturn();
    }
}
