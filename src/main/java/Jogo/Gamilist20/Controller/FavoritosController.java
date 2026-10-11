package Jogo.Gamilist20.Controller;

import Jogo.Gamilist20.DTO.FavoritoDTO;
import Jogo.Gamilist20.Modelo.JogosFavoritos;
import Jogo.Gamilist20.Modelo.StatusJogo;
import Jogo.Gamilist20.Servicos.BancoDados;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritosController {

    private final BancoDados bancoDados;

    public FavoritosController(BancoDados bancoDados) {
        this.bancoDados = bancoDados;
    }

    @GetMapping
    public List<FavoritoDTO> listar() {
        return bancoDados.listaFavoritos()
                .stream()
                .map(FavoritoDTO::de)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @PathVariable("id") Long id
    ) {
        bancoDados.removerFavorito(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public FavoritoDTO alterarStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") StatusJogo status
    ) {
        JogosFavoritos favorito =
                bancoDados.redefinirStatus(id, status);

        return FavoritoDTO.de(favorito);
    }
}