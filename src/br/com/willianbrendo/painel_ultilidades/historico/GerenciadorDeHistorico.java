package br.com.willianbrendo.painel_ultilidades.historico;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class GerenciadorDeHistorico {

    private static final String NOME_ARQUIVO = "historico.txt";
    private static final DateTimeFormatter FORMATADOR_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    public static synchronized void salvar(String registro) {

        try (FileWriter fw = new FileWriter(NOME_ARQUIVO, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            String timestamp = LocalDateTime.now().format(FORMATADOR_DATA_HORA);
            out.println("[" + timestamp + "] " + registro);

        } catch (IOException e) {
            System.err.println("Erro ao salvar o histórico: " + e.getMessage());
        }
    }

    public static List<String> lerHistorico() {
        // Verifica se o arquivo existe antes de tentar ler
        if (!Files.exists(Paths.get(NOME_ARQUIVO))) {
            return Collections.emptyList(); // Retorna lista vazia se o arquivo não existir
        }

        try {
            // Lê todas as linhas do arquivo de uma só vez
            return Files.readAllLines(Paths.get(NOME_ARQUIVO), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Erro ao ler o histórico: " + e.getMessage());
            return Collections.emptyList(); // Retorna lista vazia em caso de erro
        }
    }
}