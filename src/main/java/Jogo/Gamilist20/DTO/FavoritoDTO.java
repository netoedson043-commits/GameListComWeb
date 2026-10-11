package Jogo.Gamilist20.DTO;

import Jogo.Gamilist20.Modelo.JogosFavoritos;
import Jogo.Gamilist20.Modelo.StatusJogo;

public record FavoritoDTO(
        Long id,
        String nome,
        String ano,
        String imagem,
        StatusJogo statusJogo,
        DesenvolvedoraDTO desenvolvedor
) {

    public static FavoritoDTO de(JogosFavoritos jogo) {
        DesenvolvedoraDTO dev = null;

        if (jogo.getDesenvolvedor() != null) {
            dev = DesenvolvedoraDTO.de(
                    jogo.getDesenvolvedor()
            );
        }

        return new FavoritoDTO(
                jogo.getId(),
                jogo.getNome(),
                jogo.getAno(),
                jogo.getImagem(),
                jogo.getStatusJogo(),
                dev
        );
    }
}