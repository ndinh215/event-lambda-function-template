package com.slf.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Member {

    String memberId;
    String memberAddress;
    String memberName;

    @DynamoDbPartitionKey
    @DynamoDbAttribute(value="memberId")
    public String getMemberId() {
        return memberId;
    }
}
