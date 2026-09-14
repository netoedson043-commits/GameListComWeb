package Jogo.Gamilist20.Modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "desenvolvedor")
public class Desenvolvedora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String nome;

    @OneToMany(mappedBy = "desenvolvedor")
    private List<JogosFavoritos> jogos = new ArrayList<>();

    public Desenvolvedora() {
    }

    public Desenvolvedora(String nome) {
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<JogosFavoritos> getJogos() {
        return jogos;
    }

    public void setJogos(List<JogosFavoritos> jogos) {
        this.jogos = jogos;
    }
}