package com.slf.services;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.QueryRequest;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.util.HashMap;
import java.util.Map;

public class DynamoDbServiceImpl implements DbService{

    @Override
    public int queryTable(String regionName, String tableName, Map<String, String> keyValues, Map<String, String> aliasList) {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.of(regionName);
        DynamoDbClient ddb = DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(region)
                .build();

        HashMap<String, AttributeValue> attrValues = new HashMap<>();
        keyValues.forEach((fieldName, value) -> {
            attrValues.put(":" + fieldName, AttributeValue.builder()
                    .s(value)
                    .build());
        });

        StringBuilder keyCondition = new StringBuilder();
        for (String fieldName: aliasList.keySet()) {
            if (!keyCondition.toString().equals("")) {
                keyCondition.append(" and ");
            }

            keyCondition.append(aliasList.get(fieldName)).append("= :").append(fieldName);
        }


        QueryRequest queryReq = QueryRequest.builder()
                .tableName(tableName)
                .expressionAttributeValues(attrValues)
                .keyConditionExpression(keyCondition.toString())
                .build();

        try {
            QueryResponse response = ddb.query(queryReq);
            return response.count();
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }
}
