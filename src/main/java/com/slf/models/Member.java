package com.slf.models;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;


@DynamoDbBean
public class Member {

    private String evtId;
    private String caseId;
    private String stat;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute(value = "evt_id")
    public String getEvtId() {
        return evtId;
    }

    public void setEvtId(String evtId) {
        this.evtId = evtId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute(value = "case_id")
    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
