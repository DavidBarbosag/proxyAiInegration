package com.aygo.aiintegration.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/*
 * Adaptador que actúa como proxy para seleccionar entre ChatGPT y Copilot, además de validar la entrada.
 */
@Component
@Primary
public class ProxyAdapter implements IAiAdapter {
    private final IAiAdapter chatGptAdapter;
    private final IAiAdapter copilotAdapter;
    private final InputValidator validator;

    @Autowired
    public ProxyAdapter(
            @Qualifier("chatGptAdapter") IAiAdapter chatGptAdapter,
            @Qualifier("copilotAdapter") IAiAdapter copilotAdapter,
            InputValidator validator
    ) {
        this.chatGptAdapter = chatGptAdapter;
        this.copilotAdapter = copilotAdapter;
        this.validator = validator;
    }

    @Override
    public String generateResponse(String input) {
        String processedInput = validator.validateAndProcess(input);

        IAiAdapter targetAdapter = shouldUseCopilot(processedInput) ?
                copilotAdapter : chatGptAdapter;

        return targetAdapter.generateResponse(processedInput);
    }

    @Override
    public String getEstado() {
        return "proxy";
    }

    private boolean shouldUseCopilot(String input) {
        return input.matches(".*(def|function|class|import|public).*");
    }
}