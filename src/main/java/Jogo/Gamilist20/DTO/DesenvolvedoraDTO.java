package Jogo.Gamilist20.DTO;

import Jogo.Gamilist20.Modelo.Desenvolvedora;

public record DesenvolvedoraDTO(
        Long id,
        String nome
) {

    public static DesenvolvedoraDTO de(Desenvolvedora dev) {
        return new DesenvolvedoraDTO(
                dev.getId(),
                dev.getNome()
        );
    }
}