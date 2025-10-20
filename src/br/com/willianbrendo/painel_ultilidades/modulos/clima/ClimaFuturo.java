package br.com.willianbrendo.painel_ultilidades.modulos.clima;

public record ClimaFuturo(String nomeCidade,
                          String data,
                          double tempMedia,
                          String condicao,
                          int umidadeMedia) {

    @Override
    public String toString() {
        return """
               \n--- Previsão para %s em %s ---
               🌡️  Temp. Média: %.1f°C
               🌦️  Condição: %s
               💧 Umidade Média: %d%%
               -----------------------------------
               """.formatted(nomeCidade, data, tempMedia, condicao, umidadeMedia);
    }
}
