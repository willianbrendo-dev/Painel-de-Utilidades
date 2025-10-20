package br.com.willianbrendo.painel_ultilidades.modulos.filme;

import br.com.willianbrendo.painel_ultilidades.historico.GerenciadorDeHistorico;
import br.com.willianbrendo.painel_ultilidades.http.ClienteHttp;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BuscadorDeFilmes {
    private static final String API_KEY = ""; // <-- COLOQUE SUA CHAVE DA OMDB AQUI
    private static final String BASE_URL = "http://www.omdbapi.com/?";

    private final Scanner scanner;
    private final ClienteHttp clienteHttp;
    private final Gson gson;

    public BuscadorDeFilmes(Scanner scanner, ClienteHttp clienteHttp, Gson gson) {
        this.scanner = scanner;
        this.clienteHttp = clienteHttp;
        this.gson = gson;
    }

    public void executar() {
        while (true) {
            System.out.println("---------------------------------");
            System.out.print("Digite o nome do filme para busca (ou '0' para voltar): ");
            String busca = scanner.nextLine();

            // Condição de saída (agora volta ao menu principal)
            if (busca.trim().equalsIgnoreCase("0")) {
                System.out.println("Voltando ao menu principal...");
                return;
            }

            try {
                String buscaCodificada = URLEncoder.encode(busca, StandardCharsets.UTF_8);
                // Usando plot=full para a sinopse completa (versão "top")
                String url = BASE_URL + "t=" + buscaCodificada + "&plot=full" + "&apikey=" + API_KEY;

                String resposta = clienteHttp.buscarDados(url);

                FilmeInfo filmeInfo = gson.fromJson(resposta, FilmeInfo.class);

                // Validação se o filme foi encontrado (OMDb retorna {"Response":"False",...})
                if (filmeInfo.Title() == null) {
                    System.out.println("Filme não encontrado. Verifique o título e tente novamente.");
                    continue; // Volta para o início do while
                }

                String log = String.format(
                        "[FILME] Busca: '%s' -> Encontrado: %s (%s)",
                        busca, // O termo que o usuário buscou
                        filmeInfo.Title(),
                        filmeInfo.Year()
                );
                GerenciadorDeHistorico.salvar(log);

                // --- Impressão "Top" da Ficha Técnica ---
                System.out.println("\n===== FICHA TÉCNICA =====");
                System.out.println("Filme: " + filmeInfo.Title() + " (" + filmeInfo.Year() + ")");
                System.out.println("Gênero: " + filmeInfo.Genre());
                System.out.println("Duração: " + filmeInfo.Runtime());
                System.out.println("-------------------------");
                System.out.println("Diretor: " + filmeInfo.Director());
                System.out.println("Atores: " + filmeInfo.Actors());
                System.out.println("-------------------------");
                System.out.println("Sinopse: " + filmeInfo.Plot());
                System.out.println("-------------------------");

                System.out.println("Avaliações:");
                if (filmeInfo.imdbRating() != null && !filmeInfo.imdbRating().equalsIgnoreCase("N/A")) {
                    System.out.println("  IMDb: " + filmeInfo.imdbRating() + "/10");
                }

                // Loop para encontrar Rotten Tomatoes (o diferencial "top")
                if (filmeInfo.Ratings() != null && !filmeInfo.Ratings().isEmpty()) {
                    for (Rating rating : filmeInfo.Ratings()) {
                        if (rating.Source().equalsIgnoreCase("Rotten Tomatoes")) {
                            System.out.println("  Rotten Tomatoes: " + rating.Value());
                            break;
                        }
                    }
                }
                System.out.println("===========================\n");

            } catch (JsonSyntaxException e) {
                System.out.println("Erro ao interpretar a resposta da API.");
            } catch (Exception e) {
                System.out.println("Erro ao realizar a busca: " + e.getMessage());
            }
        }
    }
}
