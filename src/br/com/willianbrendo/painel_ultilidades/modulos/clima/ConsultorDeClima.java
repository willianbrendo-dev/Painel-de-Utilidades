package br.com.willianbrendo.painel_ultilidades.modulos.clima;

import br.com.willianbrendo.painel_ultilidades.historico.GerenciadorDeHistorico;
import br.com.willianbrendo.painel_ultilidades.http.ClienteHttp;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsultorDeClima {
    private static final String API_KEY = ""; // <-- COLOQUE SUA CHAVE DA WEATHERAPI AQUI
    private static final String BASE_URL = "https://api.weatherapi.com/v1/";

    private final Scanner scanner;
    private final ClienteHttp clienteHttp;

    public ConsultorDeClima(Scanner scanner, ClienteHttp clienteHttp) {
        this.scanner = scanner;
        this.clienteHttp = clienteHttp;
    }

    public void executar() {
        while (true) {
            System.out.println("\n=== 🌤️ CONSULTA DE CLIMA ===");
            System.out.println("1️⃣  Clima atual");
            System.out.println("2️⃣  Previsão futura (data específica)");
            System.out.println("0️⃣  Voltar ao menu principal"); // Alterado de "Sair"
            System.out.print("👉 Escolha uma opção: ");

            String opcaoStr = scanner.nextLine().trim();
            int opcao;

            try {
                opcao = Integer.parseInt(opcaoStr);
            } catch (NumberFormatException e) {
                System.out.println("❌ Opção inválida, digite um número.");
                continue;
            }

            if (opcao == 0) {
                System.out.println("👋 Voltando ao menu principal...");
                return; // Retorna para o Main.java
            }

            System.out.print("🏙️  Digite o nome da cidade: ");
            String cidade = scanner.nextLine().trim();
            String cidadeCodificada = URLEncoder.encode(cidade, StandardCharsets.UTF_8);

            String url;
            String dataInput = null; // Data que o usuário digitou
            switch (opcao) {
                case 1 -> url = BASE_URL + "current.json?key=" + API_KEY + "&q=" + cidadeCodificada + "&lang=pt";
                case 2 -> {
                    System.out.print("📅 Digite a data desejada (AAAA-MM-DD): ");
                    dataInput = scanner.nextLine().trim();
                    // A API de previsão futura ("forecast") também retorna o dia atual
                    // Se a data for futura (além de 10 dias), a API gratuita pode dar erro.
                    url = BASE_URL + "forecast.json?key=" + API_KEY + "&q=" + cidade + "&lang=pt&dt=" + dataInput;
                }
                default -> {
                    System.out.println("❌ Opção inválida, tente novamente.");
                    continue;
                }
            }

            try {
                // Exatamente a mesma lógica de parsing manual que você criou
                String resposta = clienteHttp.buscarDados(url);
                JsonObject root = JsonParser.parseString(resposta).getAsJsonObject();
                JsonObject location = root.getAsJsonObject("location");

                if (opcao == 1) {
                    JsonObject current = root.getAsJsonObject("current");
                    JsonObject condition = current.getAsJsonObject("condition");

                    ClimaInfo clima = new ClimaInfo(
                            location.get("name").getAsString(),
                            current.get("temp_c").getAsDouble(),
                            condition.get("text").getAsString(),
                            current.get("humidity").getAsInt()
                    );
                    System.out.println(clima);

                    String log = String.format(
                            "[CLIMA] Busca Atual: '%s' -> Encontrado: %s, %.1f°C, %s",
                            cidade, // O termo da busca
                            clima.nomeCidade(),
                            clima.temperatura(),
                            clima.condicao()
                    );
                    GerenciadorDeHistorico.salvar(log);

                } else { // Opção 2
                    JsonObject forecastDay = root.getAsJsonObject("forecast")
                            .getAsJsonArray("forecastday")
                            .get(0).getAsJsonObject() // Pega o primeiro (e único) dia da previsão
                            .getAsJsonObject("day");

                    JsonObject condition = forecastDay.getAsJsonObject("condition");

                    ClimaFuturo futuro = new ClimaFuturo(
                            location.get("name").getAsString(),
                            dataInput, // Usamos a data que o usuário digitou
                            forecastDay.get("avgtemp_c").getAsDouble(),
                            condition.get("text").getAsString(),
                            (int) forecastDay.get("avghumidity").getAsDouble() // API retorna double, convertemos
                    );
                    System.out.println(futuro);

                    String log = String.format(
                            "[CLIMA] Busca Futura: '%s' (%s) -> Encontrado: Temp. Média %.1f°C, %s",
                            cidade,    // O termo da busca
                            dataInput, // A data da busca
                            futuro.tempMedia(),
                            futuro.condicao()
                    );
                    GerenciadorDeHistorico.salvar(log);

                }
            } catch (Exception e) {
                System.out.println("⚠️ Erro ao buscar dados: " + e.getMessage());
                System.out.println("   Verifique o nome da cidade ou a data (formato AAAA-MM-DD).");
            }
        }
    }
}
