package br.com.willianbrendo.painel_ultilidades.modulos.moeda;

public record MoedaInfo(
        String base_code,
        String target_code,
        double conversion_rate,
        double conversion_result,
        String time_last_update_utc
) {
}
