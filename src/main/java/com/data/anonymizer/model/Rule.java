package com.data.anonymizer.model;

import lombok.Data;

@Data
public class Rule {
    private String field;
    private String type;
    private String strategy;
}
