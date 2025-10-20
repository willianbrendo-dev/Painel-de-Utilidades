package br.com.willianbrendo.painel_ultilidades.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteHttp {
    public String buscarDados(String url) {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString());
            return resposta.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro ao buscar dados da API: " + e.getMessage());
        }
    }
}
