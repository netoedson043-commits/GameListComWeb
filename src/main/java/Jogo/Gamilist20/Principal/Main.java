package Jogo.Gamilist20.Principal;

import Jogo.Gamilist20.Principal.BuscarApi.BuscarJogos;
import Jogo.Gamilist20.Servicos.BancodadosDev;
import Jogo.Gamilist20.Servicos.Menus.Menus;
import Jogo.Gamilist20.Modelo.JsonAlias.JogoRawg;
import Jogo.Gamilist20.Servicos.BancoDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
@Component
public class Main {
    boolean loop = true;
    Scanner scanner = new Scanner(System.in);

    Menus menus = new Menus();

    @Autowired
    private BancoDados banco;
    @Autowired
    private BancodadosDev bancoDev;
    @Autowired
    private BuscarJogos encontrado;

    public void principal() {
        while (loop) {
            menus.menuPrincipal();
            String desejo = scanner.nextLine();
            switch (desejo) {
                case "1":
                    encontrado.procurarJogo();
                    break;
                case "2":
                    boolean loopSecundario = true;
                    while (loopSecundario) {
                        menus.menuJogosFavoritos();
                        String desejo2 = scanner.nextLine();
                        switch (desejo2){
                            case "1":
                                JogoRawg jogoRawg = encontrado.procurarJogo();
                                banco.salvarFavoritos(jogoRawg);
                                break;

                            case "2":
                                banco.removerFavorito();
                                break;
                            case "3":
                                banco.listaFavoritos();
                                break;

                            case "4":
                                banco.redefinirStatus();
                                break;

                            case "5":
                                System.out.println("voltando");
                                loopSecundario = false;
                                break;
                            default:
                                System.out.println("Opacao invalida");
                                break;
                    }
                    }
                    break;
                case "3":
                    boolean loopTerciario = true;
                    while (loopTerciario) {
                        menus.menuDesenvolvedor();
                        String desejo3 = scanner.nextLine();
                        switch (desejo3) {
                            case "1":
                                bancoDev.listarDesenvolvedoras();
                                break;
                            case "2":
                                bancoDev.removerDesenvolvedora();
                                break;
                            case "3":
                                bancoDev.listarPorDesenvolvedora();
                                break;
                            case "4":
                                loopTerciario = false;
                                break;
                            default:
                                System.out.println("Opacao invalida");
                                break;
                    }
                    }
                    break;

                case "4":
                    loop = false;
                    break;
                default:
                    System.out.println("Opacao invalida");
                    break;
            }
        }
    }
}
