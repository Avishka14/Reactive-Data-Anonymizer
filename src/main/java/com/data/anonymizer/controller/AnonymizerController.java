package com.data.anonymizer.controller;

import com.data.anonymizer.service.AnonymizerService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class AnonymizerController {

    private final AnonymizerService anonymizerService;

    public AnonymizerController(AnonymizerService anonymizerService) {
        this.anonymizerService = anonymizerService;
    }

    @PostMapping(value = "/anonymize", consumes = MediaType.APPLICATION_NDJSON_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<JsonNode> anonymize(@RequestBody Flux<JsonNode> input) {
        return input.map(anonymizerService::anonymize);
    }
}