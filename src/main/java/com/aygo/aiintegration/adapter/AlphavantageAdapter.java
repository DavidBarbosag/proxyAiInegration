package com.aygo.aiintegration.adapter;

import com.aygo.aiintegration.controller.StockController;
import org.springframework.stereotype.Component;

@Component
public class AlphavantageAdapter {

    private final StockController controller;

    public AlphavantageAdapter(StockController controller) {
        this.controller = controller;
    }

    public String generateResponseStock() {
        return controller.generateResponseStock().getBody();
    }

    public String getEstado() {
        return "general";
    }
}
