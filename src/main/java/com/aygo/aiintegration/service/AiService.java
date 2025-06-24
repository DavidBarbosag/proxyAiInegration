package com.aygo.aiintegration.service;

import com.aygo.aiintegration.adapter.IAiAdapter;
import com.aygo.aiintegration.analyzer.InputAnalyzer;
import com.aygo.aiintegration.adapter.ProxyAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    private final IAiAdapter proxyAdapter;

    public AiService(IAiAdapter proxyAdapter) {
        this.proxyAdapter = proxyAdapter;

        if (proxyAdapter.getEstado().equals("proxy")) {
            List<IAiAdapter> adapters = List.of(proxyAdapter);
            adapters.stream()
                    .filter(adapter -> adapter.getEstado().equals("general"))
                    .findFirst()
                    .ifPresent(InputAnalyzer::setChatGptAdapter);
        }
    }

    /**
     * Genera una respuesta a partir de la entrada del usuario.
     *
     * @param input La entrada del usuario.
     * @return La respuesta generada por el adaptador seleccionado.
     */
    public String generateResponse(String input) {
        String cleanedInput = InputAnalyzer.cleanInput(input);
        boolean isCode = InputAnalyzer.isCode(cleanedInput);
        String newInput = InputAnalyzer.improveInput(cleanedInput, isCode);
        return proxyAdapter.generateResponse(newInput);
    }
}
