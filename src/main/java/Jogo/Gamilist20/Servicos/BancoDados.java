package Jogo.Gamilist20.Servicos;

import Jogo.Gamilist20.Modelo.Desenvolvedora;
import Jogo.Gamilist20.Modelo.JsonAlias.JogoRawg;
import Jogo.Gamilist20.Modelo.JogosFavoritos;
import Jogo.Gamilist20.Modelo.StatusJogo;
import Jogo.Gamilist20.repository.JogosFavoritosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BancoDados {

    @Autowired
    private BancodadosDev bancodadosDev;

    @Autowired
    private JogosFavoritosRepository jogosRepositorio;

    public JogosFavoritos salvarFavoritos(
            JogoRawg jogoRawg,
            StatusJogo status
    ) {
        if (jogoRawg == null) {
            throw new IllegalArgumentException("Informe um jogo");
        }

        if (status == null) {
            throw new IllegalArgumentException("Informe o status do jogo");
        }

        if (jogosRepositorio.existsByNome(jogoRawg.nome())) {
            throw new IllegalArgumentException(
                    "Jogo já está na lista de favoritos"
            );
        }

        JogosFavoritos favorito = new JogosFavoritos(jogoRawg);

        Desenvolvedora dev = bancodadosDev.buscarOuCriarDev(
                favorito.getDesenvolvedor()
        );

        favorito.setDesenvolvedor(dev);
        favorito.setStatusJogo(status);

        return jogosRepositorio.save(favorito);
    }

    public void removerFavorito(Long id) {
        JogosFavoritos jogo = jogosRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Jogo não encontrado nos favoritos"
                ));

        jogosRepositorio.delete(jogo);
    }

    public List<JogosFavoritos> listaFavoritos() {
        return jogosRepositorio.findAll();
    }

    public JogosFavoritos redefinirStatus(
            Long id,
            StatusJogo status
    ) {
        if (status == null) {
            throw new IllegalArgumentException("Informe o status do jogo");
        }

        JogosFavoritos jogo = jogosRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Jogo não encontrado nos favoritos"
                ));

        jogo.setStatusJogo(status);

        return jogosRepositorio.save(jogo);
    }
}