package com.aygo.aiintegration.service;

import com.aygo.aiintegration.adapter.AlphavantageAdapter;
import com.aygo.aiintegration.adapter.IAiAdapter;
import com.aygo.aiintegration.analyzer.InputAnalyzer;
import com.aygo.aiintegration.adapter.ProxyAdapter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    private final IAiAdapter proxyAdapter;
    private final AlphavantageAdapter stockAdapter;

    public AiService(IAiAdapter proxyAdapter, AlphavantageAdapter stockAdapter) {
        this.proxyAdapter = proxyAdapter;
        this.stockAdapter = stockAdapter;
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
        return proxyAdapter.generateResponse(input);
    }

    public String generateResponseStock(String input, String input2) {
        String answer1 = generateResponse(input);
        String answer2 = generateResponse(input2);
        String inputChat = "analiza cual es mejor inversion entre: " + answer1 + "y " + answer2;
        return stockAdapter.generateResponseStock();
    }

}
