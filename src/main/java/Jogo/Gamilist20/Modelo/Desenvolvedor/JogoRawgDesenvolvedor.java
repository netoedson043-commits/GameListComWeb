package Jogo.Gamilist20.Modelo.Desenvolvedor;

import Jogo.Gamilist20.Modelo.JsonAlias.DeveloperInfo;
import Jogo.Gamilist20.Modelo.JsonAlias.JogoRawg;

import java.util.stream.Collectors;

public class JogoRawgDesenvolvedor {
    public String filtrarNome(JogoRawg jogoRawg) {
        if (jogoRawg.desenvolvedora() == null || jogoRawg.desenvolvedora().isEmpty()) {
            return "Nao existe desenvolvedor";
        }
        return jogoRawg.desenvolvedora().get(0).name();
    }
}
