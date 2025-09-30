package com.data.anonymizer.service;

import com.data.anonymizer.model.Rule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RuleLoader {
    private List<Rule> rules;

    @PostConstruct
    public void loadRules() throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream in = new ClassPathResource("rules.yml").getInputStream()) {
            Map<String, Object> obj = yaml.load(in);
            Object rulesObj = obj.get("rules");

            if (rulesObj == null || !(rulesObj instanceof List<?> list)) {
                throw new IllegalStateException("No rules found in rules.yml");
            }

            rules = ((List<Map<String, Object>>) rulesObj)
                    .stream()
                    .map(m -> {
                        Rule r = new Rule();
                        r.setField((String) m.get("field"));
                        r.setType((String) m.get("type"));
                        r.setStrategy((String) m.get("strategy"));
                        return r;
                    })
                    .toList();

            log.info("Loaded {} anonymization rules", rules.size());
            rules.forEach(r ->
                    log.info("Rule: field={}, type={}, strategy={}",
                            r.getField(), r.getType(), r.getStrategy()));
        }
    }

    public List<Rule> getRules() {
        return rules;
    }
}
