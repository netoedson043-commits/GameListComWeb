package Jogo.Gamilist20.Principal.BuscarApi;

import Jogo.Gamilist20.Api.RawgApiClientHttp;
import Jogo.Gamilist20.Modelo.JsonAlias.JogoRawg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class BuscarJogos {

    @Value("${rawg.api.key}")
    private String apiKey;

    public List<JogoRawg> buscarJogos(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                    "Informe o nome do jogo"
            );
        }

        RawgApiClientHttp cliente = new RawgApiClientHttp();
        ObjectMapper jackson = new ObjectMapper();

        String nomeCodificado = URLEncoder.encode(
                nome.trim(),
                StandardCharsets.UTF_8
        );

        String endereco = "https://api.rawg.io/api/games?key="
                + apiKey
                + "&search="
                + nomeCodificado;

        String resultado = cliente.obterDados(endereco);
        List<JogoRawg> jogos = new ArrayList<>();

        try {
            JsonNode raiz = jackson.readTree(resultado);
            JsonNode results = raiz.get("results");

            if (results == null || !results.isArray()) {
                throw new RuntimeException(
                        "A API retornou uma resposta de busca inválida"
                );
            }

            for (JsonNode item : results) {
                JogoRawg jogo = jackson.treeToValue(
                        item,
                        JogoRawg.class
                );

                jogos.add(jogo);
            }

            return jogos;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Erro ao interpretar os resultados da busca",
                    e
            );
        }
    }


    public JogoRawg buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Informe um ID de jogo válido"
            );
        }

        RawgApiClientHttp cliente = new RawgApiClientHttp();
        ObjectMapper jackson = new ObjectMapper();

        String endereco = "https://api.rawg.io/api/games/"
                + id
                + "?key="
                + apiKey;

        String resultado = cliente.obterDados(endereco);

        try {
            return jackson.readValue(
                    resultado,
                    JogoRawg.class
            );

        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Erro ao interpretar os detalhes do jogo",
                    e
            );
        }
    }
}