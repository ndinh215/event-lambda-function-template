package com.slf.services;

import com.slf.models.Member;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.utils.AttributeMap;

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

    @Override
    public boolean updateField(String regionName, String tableName, String keyValue, String fieldName, String value) {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.of(regionName);
        DynamoDbClient ddb = DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(ddb).build();
        DynamoDbTable<Member> table = enhancedClient.table(tableName, TableSchema.fromBean(Member.class));

        Key key = Key.builder().partitionValue(keyValue).build();
        Member member = table.getItem(r->r.key(key));
        member.setMemberName("Updated");

        return true;
    }
}
