package Jogo.Gamilist20.Controller;

import Jogo.Gamilist20.DTO.FavoritoDTO;
import Jogo.Gamilist20.DTO.JogoBuscaDTO;
import Jogo.Gamilist20.Modelo.JsonAlias.JogoRawg;
import Jogo.Gamilist20.Modelo.JogosFavoritos;
import Jogo.Gamilist20.Modelo.StatusJogo;
import Jogo.Gamilist20.Principal.BuscarApi.BuscarJogos;
import Jogo.Gamilist20.Servicos.BancoDados;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogos")
public class JogosController {

    private final BuscarJogos buscarJogos;
    private final BancoDados bancoDados;

    public JogosController(
            BuscarJogos buscarJogos,
            BancoDados bancoDados
    ) {
        this.buscarJogos = buscarJogos;
        this.bancoDados = bancoDados;
    }

    @GetMapping("/buscar")
    public List<JogoBuscaDTO> buscar(
            @RequestParam("nome") String nome
    ) {
        return buscarJogos.buscarJogos(nome)
                .stream()
                .map(JogoBuscaDTO::de)
                .toList();
    }

    @PostMapping("/favoritos/{id}")
    public ResponseEntity<FavoritoDTO> adicionarFavorito(
            @PathVariable("id") Long id,
            @RequestParam("status") StatusJogo status
    ) {
        JogoRawg jogo = buscarJogos.buscarPorId(id);

        JogosFavoritos favorito =
                bancoDados.salvarFavoritos(jogo, status);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(FavoritoDTO.de(favorito));
    }
}