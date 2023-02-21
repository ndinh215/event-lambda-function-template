package com.slf.services;

import com.slf.models.Member;

import java.util.Map;

public interface DbService {
    int queryTable(String regionName, String tableName, Map<String, String> keyValues, String filter);

    boolean updateField(String regionName, String tableName, String keyValue, String sortValue, String fieldName, String value);

    boolean putItem(String regionName, String tableName, Member member);
}
