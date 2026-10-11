package Jogo.Gamilist20.DTO;

import Jogo.Gamilist20.Modelo.JsonAlias.JogoRawg;

public record JogoBuscaDTO(
        Long id,
        String nome,
        String ano,
        String imagem
) {

    public static JogoBuscaDTO de(JogoRawg jogo) {
        return new JogoBuscaDTO(
                jogo.id(),
                jogo.nome(),
                jogo.ano(),
                jogo.imagem()
        );
    }
}