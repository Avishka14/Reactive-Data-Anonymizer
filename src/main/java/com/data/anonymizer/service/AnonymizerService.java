package com.data.anonymizer.service;

import com.data.anonymizer.model.Rule;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

@Service
public class AnonymizerService {
    private final RuleLoader ruleLoader;

    public AnonymizerService(RuleLoader ruleLoader) {
        this.ruleLoader = ruleLoader;
    }

    public JsonNode anonymize(JsonNode input) {
        ObjectNode copy = input.deepCopy();

        for (Rule rule : ruleLoader.getRules()) {
            if (copy.has(rule.getField())) {
                String value = copy.get(rule.getField()).asText();
                String masked = applyRule(value, rule);
                copy.put(rule.getField(), masked);
            }
        }
        return copy;
    }

    private String applyRule(String value, Rule rule) {
        switch (rule.getStrategy()) {
            case "keep-domain":
                return "*****@" + value.split("@")[1];
            case "last-4-digits":
                return "****" + value.substring(value.length() - 4);
            default:
                return "*****";
        }
    }
}
