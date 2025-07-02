package com.aygo.aiintegration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/consult")
public class StockController {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private String apiUrl = "U7N1OGPA00M1V5BT";
    private String apiKey = "U7N1OGPA00M1V5BT";
    private String function = "function=TIME_SERIES_WEEKLY&symbol=IBM&apikey=U7N1OGPA00M1V5BT";

    public StockController(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    @PostMapping("/consultStock")
    public ResponseEntity<String> generateResponseStock() {
        try {

            // Realiza la solicitud al endpoint de OpenAI
            String response = webClient.post()
                    .uri(apiUrl)
                    .bodyValue(function)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Manejo de errores generales
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error inesperado: " + e.getMessage());
        }
    }
}
