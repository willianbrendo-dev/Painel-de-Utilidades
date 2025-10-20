package br.com.willianbrendo.painel_ultilidades.modulos.clima;

public record ClimaInfo(String nomeCidade,
                        double temperatura,
                        String condicao,
                        int umidade) {

    @Override
    public String toString() {
        return """
               \n--- Clima Atual em %s ---
               🌡️  Temperatura: %.1f°C
               🌦️  Condição: %s
               💧 Umidade: %d%%
               -----------------------------
               """.formatted(nomeCidade, temperatura, condicao, umidade);
    }
}
