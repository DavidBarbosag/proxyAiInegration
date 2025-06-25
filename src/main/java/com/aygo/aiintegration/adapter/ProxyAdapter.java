package com.aygo.aiintegration.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/*
 * Adaptador que actúa como proxy para seleccionar entre ChatGPT, además de validar la entrada.
 */
@Component
@Primary
public class ProxyAdapter implements IAiAdapter {
    private final IAiAdapter chatGptAdapter;
    private final InputValidator validator;

    public ProxyAdapter(IAiAdapter chatGptAdapter, InputValidator validator) {
        this.chatGptAdapter = chatGptAdapter;
        this.validator = validator;
    }

    @Override
    public String generateResponse(String input) {
        String processedInput = validator.validateAndProcess(input);

        IAiAdapter targetAdapter = chatGptAdapter;

        return targetAdapter.generateResponse(processedInput);
    }

    @Override
    public String getEstado() {
        return "proxy";
    }
}