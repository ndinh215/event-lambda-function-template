package com.slf.services;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

public class DynamoDbServiceImpl implements DbService{

    @Override
    public int queryTable(String regionName, String tableName, Map<String, String> keyValues, String filter) {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.of(regionName);
        DynamoDbClient ddb = DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(region)
                .build();

        HashMap<String, AttributeValue> attrValues = new HashMap<>();
        keyValues.forEach((fieldName, value) -> {
            attrValues.put(fieldName, AttributeValue.builder()
                    .s(value)
                    .build());
        });

        ScanRequest queryReq = ScanRequest.builder()
                .tableName(tableName)
                .expressionAttributeValues(attrValues)
                .filterExpression(filter)
                .build();

        try {
            ScanResponse response = ddb.scan(queryReq);
            return response.count();
        } catch (DynamoDbException ex) {
            System.err.println(ex.getMessage());
        }
        return -1;
    }
}
