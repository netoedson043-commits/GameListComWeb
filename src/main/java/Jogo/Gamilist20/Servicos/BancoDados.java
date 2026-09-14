package Jogo.Gamilist20.Servicos;

import Jogo.Gamilist20.Modelo.Desenvolvedora;
import Jogo.Gamilist20.Modelo.JsonAlias.JogoRawg;
import Jogo.Gamilist20.Modelo.JogosFavoritos;
import Jogo.Gamilist20.Modelo.StatusJogo;
import Jogo.Gamilist20.Servicos.Menus.Menus;
import Jogo.Gamilist20.repository.JogosFavoritosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class BancoDados {

    private Menus menus = new Menus();

    @Autowired
    private BancodadosDev bancodadosDev;

    @Autowired
    private JogosFavoritosRepository jogosRepositorio;

    public void salvarFavoritos(JogoRawg jogoRawg) {
       if (jogosRepositorio.existsByNome(jogoRawg.nome())) {
           System.out.println("Jogo ja esta na lista de favoritos");
       } else {
           JogosFavoritos favoritos = new JogosFavoritos(jogoRawg);

           Desenvolvedora dev = bancodadosDev.buscarOuCriarDev(favoritos.getDesenvolvedor());
           favoritos.setDesenvolvedor(dev);

           StatusJogo statusJogo = perguntarStatus();
           favoritos.setStatusJogo(statusJogo);


           jogosRepositorio.save(favoritos);
           System.out.println("jogo salvo");

       }
    }
    public void removerFavorito() {
        Scanner scanner = new Scanner(System.in);

        boolean temFavorito = listaFavoritos();
        if (!temFavorito) {
            return;
        }


        System.out.println("qual id do jogo que deseja tirar dos favoritos(ID)");
        long jogoRemove = scanner.nextLong();

        Optional<JogosFavoritos> jogo = jogosRepositorio.findById(jogoRemove);
        if (jogo.isEmpty()) {
            System.out.println("Id invalido ou nao esta nos favoritos");
        } else {
            jogosRepositorio.delete(jogo.get());
            System.out.println("removido com sucesso");
        }


    }

    public boolean listaFavoritos() {
        List<JogosFavoritos> favoritos = jogosRepositorio.findAll();

        if (favoritos.isEmpty()) {
            System.out.println("nenhum jogo nos favoritos");
            return false;
        } else {
            System.out.println("Lista de jogos Favoritos");
            for (JogosFavoritos favorito : favoritos) {
                System.out.println(favorito.getId() + " - " + favorito.getNome() + " - " + favorito.getStatusJogo());
            }
            return true;
        }
    }

    public void redefinirStatus() {
        Scanner scanner = new Scanner(System.in);
        boolean temFavorito = listaFavoritos();
        if (!temFavorito) {
            return;
        }

        System.out.println("Qual jogo deseja redefinir(ID)");
        long jogoescolhido = scanner.nextLong();
        Optional<JogosFavoritos> jogo = jogosRepositorio.findById(jogoescolhido);

        if (jogo.isEmpty()) {
            System.out.println("Id invalido");
            return;
        }
        StatusJogo statusJogo = perguntarStatus();
        JogosFavoritos jogoFavorito = jogo.get();
        jogoFavorito.setStatusJogo(statusJogo);
        jogosRepositorio.save(jogoFavorito);

        System.out.println("Atualizado com sucesso");

    }

    public StatusJogo perguntarStatus() {
        Scanner scanner = new Scanner(System.in);
        menus.menuStatus();
        String escolha = scanner.nextLine();

        return switch (escolha) {
            case "1" -> StatusJogo.VOU_JOGAR;
            case "2" -> StatusJogo.JOGANDO;
            case "3" -> StatusJogo.CONCLUIDO;
            default -> StatusJogo.SEM_INFORMACOES;

        };
    }
}
