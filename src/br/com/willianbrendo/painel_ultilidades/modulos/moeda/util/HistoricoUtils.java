package br.com.willianbrendo.painel_ultilidades.modulos.moeda.util;

import br.com.willianbrendo.painel_ultilidades.modulos.moeda.MoedaInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoricoUtils {
    private static final String ARQUIVO_HISTORICO = "historico.txt";
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // Salva uma conversão no histórico (arquivo)
    public static void salvar(MoedaInfo c) {
        try (FileWriter writer = new FileWriter(ARQUIVO_HISTORICO, true)) {
            String dataHora = LocalDateTime.now().format(FORMATADOR);
            writer.write(String.format("%s | %s -> %s | Valor convertido: %.2f | Taxa: %.4f | Atualização API: %s%n",
                    dataHora,
                    c.base_code(),
                    c.target_code(),
                    c.conversion_result(),
                    c.conversion_rate(),
                    c.time_last_update_utc()));
        } catch (IOException e) {
            System.out.println("Erro ao salvar histórico: " + e.getMessage());
        }
    }

    // Lê o histórico completo do arquivo
    public static List<String> lerHistorico() {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_HISTORICO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            // Se o arquivo não existir ainda, não precisa mostrar erro
        }
        return linhas;
    }
}
