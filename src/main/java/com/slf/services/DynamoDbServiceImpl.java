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

import java.util.HashMap;
import java.util.Map;

public class DynamoDbServiceImpl implements DbService {

    public static void updateTableItem(String regionName,
                                       String tableName,
                                       String key,
                                       String keyVal,
                                       String name,
                                       String updateVal) {
        Region region = Region.of(regionName);
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        HashMap<String, AttributeValue> itemKey = new HashMap<>();
        itemKey.put(key, AttributeValue.builder()
                .s(keyVal)
                .build());
        itemKey.put("sort_id", AttributeValue.builder()
                .s("1")
                .build());

        HashMap<String, AttributeValueUpdate> updatedValues = new HashMap<>();
        updatedValues.put(name, AttributeValueUpdate.builder()
                .value(AttributeValue.builder().s(updateVal).build())
                .action(AttributeAction.PUT)
                .build());

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(itemKey)
                .attributeUpdates(updatedValues)
                .build();

        try {
            ddb.updateItem(request);
        } catch (ResourceNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("The Amazon DynamoDB table was updated!");
    }

    @Override
    public int queryTable(String regionName, String tableName, Map<String, String> keyValues, String filter) {
        Region region = Region.of(regionName);
        DynamoDbClient ddb = DynamoDbClient.builder()
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
    public boolean updateField(String regionName, String tableName, String keyValue, String sortValue, String fieldName, String value) {
        Region region = Region.of(regionName);
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(ddb).build();
        DynamoDbTable<Member> table = enhancedClient.table(tableName, TableSchema.fromBean(Member.class));

        Key.Builder builder = Key.builder().partitionValue(keyValue);
        if (sortValue != null) {
            builder.sortValue(sortValue);
        }
        Key key = builder.build();
        Member event = table.getItem(r -> r.key(key));
        event.setStat(value);

        table.updateItem(event);

        return true;
    }

    @Override
    public boolean putItem(String regionName, String tableName, Member member) {
        Region region = Region.of(regionName);
        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(ddb).build();
        DynamoDbTable<Member> table = enhancedClient.table(tableName, TableSchema.fromBean(Member.class));

        table.putItem(member);

        return true;
    }
}
