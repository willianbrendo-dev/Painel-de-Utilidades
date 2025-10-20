package br.com.willianbrendo.painel_ultilidades.modulos.filme;

import java.util.List;

public record FilmeInfo(String Title,
                        String Year,
                        String imdbID,
                        String Type,
                        String Poster,
                        String Plot,
                        String Director,
                        String Actors,
                        String Genre,
                        String Runtime,
                        List<Rating> Ratings, // Lista do record acima
                        String imdbRating) {
}
