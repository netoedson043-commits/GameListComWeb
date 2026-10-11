package Jogo.Gamilist20.Servicos;

import Jogo.Gamilist20.Modelo.Desenvolvedora;
import Jogo.Gamilist20.Modelo.JogosFavoritos;
import Jogo.Gamilist20.repository.DesenvolvedoraRepository;
import Jogo.Gamilist20.repository.JogosFavoritosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BancodadosDev {

    @Autowired
    private JogosFavoritosRepository jogosFavoritosRepository;

    @Autowired
    private DesenvolvedoraRepository desenvolvedoraRepository;

    // Reutiliza a desenvolvedora cadastrada ou salva uma nova.
    public Desenvolvedora buscarOuCriarDev(
            Desenvolvedora desenvolvedor
    ) {
        if (desenvolvedor == null
                || desenvolvedor.getNome() == null
                || desenvolvedor.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O jogo não possui uma desenvolvedora válida"
            );
        }

        return desenvolvedoraRepository
                .findByNome(desenvolvedor.getNome())
                .orElseGet(() ->
                        desenvolvedoraRepository.save(desenvolvedor)
                );
    }

    // Retorna todas as desenvolvedoras cadastradas.
    public List<Desenvolvedora> listarDesenvolvedoras() {
        return desenvolvedoraRepository.findAll();
    }

    // Retorna os favoritos da desenvolvedora escolhida.
    public List<JogosFavoritos> listarPorDesenvolvedora(Long id) {
        buscarDesenvolvedora(id);

        return jogosFavoritosRepository
                .findByDesenvolvedor_Id(id);
    }

    // Remove somente se não houver favoritos vinculados.
    public void removerDesenvolvedora(Long id) {
        Desenvolvedora desenvolvedora = buscarDesenvolvedora(id);

        List<JogosFavoritos> jogosVinculados =
                jogosFavoritosRepository.findByDesenvolvedor_Id(id);

        if (!jogosVinculados.isEmpty()) {
            throw new IllegalArgumentException(
                    "Não é possível remover a desenvolvedora "
                            + "pois ela ainda possui jogos nos favoritos"
            );
        }

        desenvolvedoraRepository.delete(desenvolvedora);
    }

    // Método auxiliar usado para validar e buscar o ID.
    private Desenvolvedora buscarDesenvolvedora(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Informe um ID de desenvolvedora válido"
            );
        }

        return desenvolvedoraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Desenvolvedora não encontrada"
                ));
    }
}