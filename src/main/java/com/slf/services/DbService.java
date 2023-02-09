package com.slf.services;

import java.util.Map;

public interface DbService {
    int queryTable(String regionName, String tableName, Map<String, String> keyValues, Map<String, String> aliasList);
}
