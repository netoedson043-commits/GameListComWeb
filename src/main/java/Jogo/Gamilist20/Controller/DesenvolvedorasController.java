package Jogo.Gamilist20.Controller;

import Jogo.Gamilist20.DTO.DesenvolvedoraDTO;
import Jogo.Gamilist20.DTO.FavoritoDTO;
import Jogo.Gamilist20.Servicos.BancodadosDev;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desenvolvedoras")
public class DesenvolvedorasController {

    private final BancodadosDev bancodadosDev;

    public DesenvolvedorasController(
            BancodadosDev bancodadosDev
    ) {
        this.bancodadosDev = bancodadosDev;
    }

    @GetMapping
    public List<DesenvolvedoraDTO> listar() {
        return bancodadosDev.listarDesenvolvedoras()
                .stream()
                .map(DesenvolvedoraDTO::de)
                .toList();
    }

    @GetMapping("/{id}/jogos")
    public List<FavoritoDTO> listarJogos(
            @PathVariable("id") Long id
    ) {
        return bancodadosDev.listarPorDesenvolvedora(id)
                .stream()
                .map(FavoritoDTO::de)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @PathVariable("id") Long id
    ) {
        bancodadosDev.removerDesenvolvedora(id);

        return ResponseEntity.noContent().build();
    }
}