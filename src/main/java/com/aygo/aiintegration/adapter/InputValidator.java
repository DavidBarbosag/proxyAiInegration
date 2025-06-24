package com.aygo.aiintegration.adapter;

import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Valida y procesa el input del usuario para asegurar que cumple con los requisitos
 * de longitud, contenido prohibido y calidad del prompt.
 */
@Component
public class InputValidator {

    private static final List<String> PALABRAS_PROHIBIDAS = Arrays.asList(
            "hackear", "exploit", "malware", "virus",
            "odio", "matar", "suicidio", "droga", "armas"
    );

    private static final List<String> PALABRAS_CLAVE_POSITIVAS = Arrays.asList(
            "explica", "describe", "comparar", "ejemplo", "código",
            "diferencia entre", "ventajas", "desventajas", "implementar", "role",
            "rol", "formato"
    );


    /**
     * Valida y procesa el input según múltiples criterios.
     * @param input Texto del prompt a validar
     * @return Prompt procesado y validado
     * @throws IllegalArgumentException Si el prompt no cumple los requisitos
     */
    public String validateAndProcess(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("El prompt no puede estar vacío");
        }

        String processedInput = input.trim();

        validateLongitud(processedInput);

        validateContenidoProhibido(processedInput);

        validateCalidadPrompt(processedInput);

        return processedInput;
    }

    /**
     * Valida la longitud del prompt.
     * @param input Texto del prompt a validar
     * @throws IllegalArgumentException Si el prompt no cumple con los requisitos de longitud
     */
    private void validateLongitud(String input) {
        int minCaracteres = 150;
        int maxCaracteres = 2000;

        if (input.length() < minCaracteres) {
            throw new IllegalArgumentException(
                    String.format("El prompt es demasiado corto (mínimo %d caracteres)", minCaracteres)
            );
        }

        if (input.length() > maxCaracteres) {
            throw new IllegalArgumentException(
                    String.format("El prompt es demasiado largo (máximo %d caracteres)", maxCaracteres)
            );
        }
    }

    /**
     * Valida que el prompt no contenga palabras prohibidas.
     * @param input Texto del prompt a validar
     * @throws IllegalArgumentException Si el prompt contiene palabras prohibidas
     */
    private void validateContenidoProhibido(String input) {
        String inputLower = input.toLowerCase();

        PALABRAS_PROHIBIDAS.forEach(palabra -> {
            if (inputLower.contains(palabra)) {
                throw new IllegalArgumentException(
                        String.format("Contenido no permitido: '%s'", palabra)
                );
            }
        });
    }

    /**
     * Valida que el prompt contenga suficientes palabras clave positivas.
     * @param input Texto del prompt a validar
     * @throws IllegalArgumentException Si el prompt no contiene suficientes palabras clave
     */
    private void validateCalidadPrompt(String input) {
        long countPalabrasClave = PALABRAS_CLAVE_POSITIVAS.stream()
                .filter(palabra -> input.toLowerCase().contains(palabra))
                .count();

        if (countPalabrasClave <= 2) {
            throw new IllegalArgumentException(
                    "Prompt demasiado vago. Incluye términos como 'explica', 'ejemplo', 'comparar', etc."
            );
        }
    }

}
