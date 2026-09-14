package Jogo.Gamilist20.Servicos;

import Jogo.Gamilist20.Modelo.Desenvolvedora;
import Jogo.Gamilist20.Modelo.JogosFavoritos;
import Jogo.Gamilist20.repository.DesenvolvedoraRepository;
import Jogo.Gamilist20.repository.JogosFavoritosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class BancodadosDev {

    @Autowired
    private JogosFavoritosRepository jogosFavoritosRepository;

    @Autowired
    private DesenvolvedoraRepository desenvolvedoraRepository;

    public Desenvolvedora buscarOuCriarDev(Desenvolvedora desenvolvedor) {
        return desenvolvedoraRepository
                .findByNome(desenvolvedor.getNome())
                .orElseGet(() -> desenvolvedoraRepository.save(desenvolvedor));
    }

    public boolean listarDesenvolvedoras() {
        List<Desenvolvedora> desenvolvedoras = desenvolvedoraRepository.findAll();

        if (desenvolvedoras.isEmpty()) {
            System.out.println("nao existe desenvolvedora");
            return false;
        } else {
            System.out.println("Lista de desenvolvedores");
            for (Desenvolvedora desenvolvedora : desenvolvedoras) {
                System.out.println(desenvolvedora.getId() + " - " + desenvolvedora.getNome());
            }
            return true;
        }
    }

    public void listarPorDesenvolvedora() {
        Scanner scanner = new Scanner(System.in);
        listarDesenvolvedoras();

        System.out.println("Qual o Id da desenvolvedora que deseja fazer a busca");
        long idDesenvolvedora = scanner.nextLong();
        List<JogosFavoritos> jogosDesenvolvedora = jogosFavoritosRepository.findByDesenvolvedor_Id(idDesenvolvedora);

        if (jogosDesenvolvedora.isEmpty()) {
            System.out.println("Nao tem jogos nos favoritos dessa desenvolvedora");
            return;
        }

        Optional<Desenvolvedora> desenvolvedora = desenvolvedoraRepository.findById(idDesenvolvedora);
        String nomeDesenvolvedora = desenvolvedora.get().getNome();

        System.out.println("Jogos dessa Desenvolvedora: " + nomeDesenvolvedora);
        for (JogosFavoritos favoritos : jogosDesenvolvedora) {
            System.out.println(favoritos.getId() + " - " + favoritos.getNome());

        }
    }

    public void removerDesenvolvedora() {
        Scanner scanner = new Scanner(System.in);
        boolean temDesenvolvedora = listarDesenvolvedoras();

        if (!temDesenvolvedora) {
            return;
        }
        System.out.println("qual id do jogo que deseja tirar dos favoritos(ID)");
        long jogoRemove = scanner.nextLong();
        Optional<Desenvolvedora> desenvolvedora = desenvolvedoraRepository.findById(jogoRemove);
        if (desenvolvedora.isEmpty()) {
            System.out.println("Id invalido");
        }
        List<JogosFavoritos> jogovinculado = jogosFavoritosRepository.findByDesenvolvedor_Id(desenvolvedora.get().getId());
        if (!jogovinculado.isEmpty()) {
            System.out.println("Nao é possivel apagar essa desenvolvedora pois ainda tem jogos nos favortos dessa desenvolvedora");
            return;
        }

        desenvolvedoraRepository.delete(desenvolvedora.get());
        System.out.println("Desenvolvedora Removida com sucesso");

    }
}