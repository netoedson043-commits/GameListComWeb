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
import java.util.Scanner;

@Component
public class BuscarJogos {

    @Value("${rawg.api.key}")
    private String apiKey;

    public JogoRawg procurarJogo() {
        Scanner scanner = new Scanner(System.in);
        RawgApiClientHttp jogoProcurado = new RawgApiClientHttp();

        System.out.println("nome do jogo");
        String buscarJogo = scanner.nextLine();
        String buscarTransformada = URLEncoder.encode(buscarJogo, StandardCharsets.UTF_8);
        String endereco = "https://api.rawg.io/api/games?key=" + apiKey + "&search=" + buscarTransformada;

        var resultado = jogoProcurado.obterDados(endereco);
        ObjectMapper jackson = new ObjectMapper();
        List<JogoRawg> listaJogos = new ArrayList<>();

        try {
            JsonNode raiz = jackson.readTree(resultado);
            JsonNode results = raiz.get("results");

            if (results == null || results.isEmpty()) {
                System.out.println("Nenhum jogo encontrado");
                return null;
            }

            for (JsonNode item : results) {
                JogoRawg jogo = jackson.treeToValue(item, JogoRawg.class);
                listaJogos.add(jogo);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Resultados encontrados:");
        for (int i = 0; i < listaJogos.size(); i++) {
            System.out.println((i + 1) + " - " + listaJogos.get(i).nome() + " (" + listaJogos.get(i).ano() + ")");
        }



        System.out.println("Escolha o numero do jogo:");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        JogoRawg escolhido = listaJogos.get(escolha - 1);
        String enderecoDetalhe = "https://api.rawg.io/api/games/" + escolhido.id() + "?key=" + apiKey;
        var resultadoDetalhe = jogoProcurado.obterDados(enderecoDetalhe);

        JogoRawg jogoCompleto;
        try {
            jogoCompleto = jackson.readValue(resultadoDetalhe, JogoRawg.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println(jogoCompleto);
        return jogoCompleto;
    }
}