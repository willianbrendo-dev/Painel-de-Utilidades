package br.com.willianbrendo.painel_ultilidades.modulos.moeda;

import br.com.willianbrendo.painel_ultilidades.historico.GerenciadorDeHistorico;
import br.com.willianbrendo.painel_ultilidades.http.ClienteHttp;
import br.com.willianbrendo.painel_ultilidades.modulos.moeda.util.HistoricoUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConversorDeMoeda {
    private final Scanner scanner;
    private final ClienteHttp clienteHttp;
    private final Gson gson;

    private final List<MoedaInfo> historicoSessao = new ArrayList<>();

    private static final String API_KEY = "SUA_CHAVE";

    public ConversorDeMoeda(Scanner scanner, ClienteHttp clienteHttp, Gson gson) {
        this.scanner = scanner;
        this.clienteHttp = clienteHttp;
        this.gson = gson;
    }

    public void executar() {
        Locale.setDefault(Locale.US);
        while (true) {
            System.out.println("\n=== Conversor de Moeda (Tempo Real) ===");
            System.out.println("Escolha a conversão:");
            System.out.println("[1] BRL -> USD");
            System.out.println("[2] USD -> BRL");
            System.out.println("[3] USD -> ARS (Peso Argentino)");
            System.out.println("[4] ARS -> USD");
            System.out.println("[5] EUR -> BRL");
            System.out.println("[6] BRL -> EUR");
            System.out.println("[7] Mostrar histórico (sessão)");
            System.out.println("[8] Voltar ao menu principal"); // Alterado de "Sair"
            System.out.print("Opção: ");

            String opt = scanner.nextLine().trim();

            switch (opt) {
                case "1" -> realizarConversao("BRL", "USD");
                case "2" -> realizarConversao("USD", "BRL");
                case "3" -> realizarConversao("USD", "ARS");
                case "4" -> realizarConversao("ARS", "USD");
                case "5" -> realizarConversao("EUR", "BRL");
                case "6" -> realizarConversao("BRL", "EUR");
                case "7" -> mostrarHistorico();
                case "8" -> {
                    System.out.println("Voltando ao menu principal...");
                    return; // Retorna para quem o chamou (o futuro Main.java)
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void realizarConversao(String de, String para) {
        System.out.printf("Você escolheu converter de %s para %s%n", de, para);
        System.out.print("Digite o valor: ");
        String input = scanner.nextLine().replace(",", ".");

        try {
            double valorNum = Double.parseDouble(input); // Validação antecipada

            String url = String.format(
                    "https://v6.exchangerate-api.com/v6/%s/pair/%s/%s/%.2f",
                    API_KEY, de, para, valorNum
            );

            String resposta = clienteHttp.buscarDados(url); // Assumindo que seu cliente tem este método

            MoedaInfo conversao = gson.fromJson(resposta, MoedaInfo.class);

            String log = String.format(
                    "[MOEDA] Conversão: %.2f %s -> %.2f %s (Taxa: %.4f)",
                    valorNum, // O valor que o usuário digitou
                    conversao.base_code(),
                    conversao.conversion_result(),
                    conversao.target_code(),
                    conversao.conversion_rate()
            );

            GerenciadorDeHistorico.salvar(log);

            System.out.println("\n=== Resultado da Conversão ===");
            System.out.printf("De: %s\nPara: %s\nTaxa: %.4f\nValor convertido: %.2f\nÚltima atualização: %s\n",
                    conversao.base_code(),
                    conversao.target_code(),
                    conversao.conversion_rate(),
                    conversao.conversion_result(),
                    conversao.time_last_update_utc()
            );

            // Adiciona ao histórico desta sessão
            historicoSessao.add(conversao);

            // Salva no arquivo persistente (usando a nova classe de histórico)
            //HistoricoUtils.salvar(conversao); // Assumindo método estático

        } catch (NumberFormatException e) {
            System.out.println("Valor inválido! Digite apenas números.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar dados da API: " + e.getMessage());
        }
    }

    private void mostrarHistorico() {
        System.out.println("\n=== Histórico de Conversões (sessão) ===");

        // Usando a nova classe de histórico
        //List<String> historicoArquivo = HistoricoUtils.lerHistorico(); // Assumindo método estático

        if (historicoSessao.isEmpty()) {
            System.out.println("Nenhuma conversão realizada ainda.");
            return;
        }

        System.out.println("--- Histórico da Sessão Atual ---");
        // Mostrar histórico em memória
        for (MoedaInfo c : historicoSessao) {
            System.out.printf("%s -> %s | Valor convertido: %.2f | Taxa: %.4f | API: %s%n",
                    c.base_code(), c.target_code(), c.conversion_result(), c.conversion_rate(), c.time_last_update_utc());
        }

//        System.out.println("--- Histórico Persistente (Arquivo) ---");
//        // Mostrar histórico do arquivo
//        for (String linha : historicoArquivo) {
//            System.out.println(linha);
//        }
    }
}
