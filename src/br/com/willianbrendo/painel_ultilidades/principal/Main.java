package br.com.willianbrendo.painel_ultilidades.principal;

import br.com.willianbrendo.painel_ultilidades.historico.GerenciadorDeHistorico;
import br.com.willianbrendo.painel_ultilidades.http.ClienteHttp;
import br.com.willianbrendo.painel_ultilidades.modulos.clima.ConsultorDeClima;
import br.com.willianbrendo.painel_ultilidades.modulos.filme.BuscadorDeFilmes;
import br.com.willianbrendo.painel_ultilidades.modulos.moeda.ConversorDeMoeda;
import com.google.gson.Gson;


import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            ClienteHttp clienteHttp = new ClienteHttp();

            Gson gson = new Gson();

            ConversorDeMoeda conversorMoeda = new ConversorDeMoeda(scanner, clienteHttp, gson);
            ConsultorDeClima consultorClima = new ConsultorDeClima(scanner, clienteHttp);
            BuscadorDeFilmes buscadorFilmes = new BuscadorDeFilmes(scanner, clienteHttp, gson);

            while (true) {
                System.out.println("\n--- PAINEL DE UTILIDADES ---");
                System.out.println("[1] Conversor de Moeda");
                System.out.println("[2] Consultar Clima");
                System.out.println("[3] Buscar Filme");
                System.out.println("[4] Ver Histórico de Pesquisas");
                System.out.println("[0] Sair");
                System.out.print("Escolha uma opção: ");

                String opcao = scanner.nextLine().trim();

                switch (opcao) {
                    case "1":
                        conversorMoeda.executar();
                        break;
                    case "2":
                        consultorClima.executar();
                        break;
                    case "3":
                        buscadorFilmes.executar();
                        break;
                    case "4":
                        mostrarHistoricoGeral();
                        break;
                    case "0":
                        System.out.println("Encerrando o Painel de Utilidades. Até logo!");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }

        } catch (Exception e) {
            System.err.println("Ocorreu um erro fatal no programa: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarHistoricoGeral() {
        System.out.println("\n=== Histórico Geral de Operações ===");

        List<String> historico = GerenciadorDeHistorico.lerHistorico();

        if (historico.isEmpty()) {
            System.out.println("Nenhuma operação registrada no histórico ainda.");
            return;
        }

        System.out.println("--- Log de Atividades ---");
        for (String linha : historico) {
            System.out.println(linha);
        }
    }
}