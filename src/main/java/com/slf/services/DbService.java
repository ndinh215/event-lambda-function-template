package com.slf.services;

import java.util.Map;

public interface DbService {
    int queryTable(String regionName, String tableName, Map<String, String> keyValues, String filter);

    boolean updateField(String regionName, String tableName, String keyValue, String fieldName, String value);
}
