package Jogo.Gamilist20.Modelo;

import Jogo.Gamilist20.Modelo.Desenvolvedor.JogoRawgDesenvolvedor;
import Jogo.Gamilist20.Modelo.JsonAlias.JogoRawg;
import jakarta.persistence.*;

@Entity
@Table(name = "jogosFavoritos")
public class JogosFavoritos {
    @Transient
    JogoRawgDesenvolvedor jogoRawgDesenvolvedor = new JogoRawgDesenvolvedor();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;

    @ManyToOne
    @JoinColumn(name = "desenvolvedorId")
    private Desenvolvedora desenvolvedor;

    private Integer avaliacaoRawg;
    private String ano;
    private String imagem;

    @Enumerated(EnumType.STRING)
    private StatusJogo statusJogo;


    public JogosFavoritos() {
    }


    public JogosFavoritos(JogoRawg jogoRawg) {
        this.nome = jogoRawg.nome();
        String nomedesenvolvedor = jogoRawgDesenvolvedor.filtrarNome(jogoRawg);
        this.desenvolvedor = new Desenvolvedora(nomedesenvolvedor);
        this.avaliacaoRawg = jogoRawg.avaliacaoRawg() != null ? jogoRawg.avaliacaoRawg() : 0;
        this.ano = jogoRawg.ano();
        this.imagem = jogoRawg.imagem();

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

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Integer getAvaliacaoRawg() {
        return avaliacaoRawg;
    }

    public void setAvaliacaoRawg(Integer avaliacaoRawg) {
        this.avaliacaoRawg = avaliacaoRawg;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Desenvolvedora getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(Desenvolvedora desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public JogoRawgDesenvolvedor getJogoRawgDesenvolvedor() {
        return jogoRawgDesenvolvedor;
    }

    public void setJogoRawgDesenvolvedor(JogoRawgDesenvolvedor jogoRawgDesenvolvedor) {
        this.jogoRawgDesenvolvedor = jogoRawgDesenvolvedor;
    }

    public StatusJogo getStatusJogo() {
        return statusJogo;
    }

    public void setStatusJogo(StatusJogo statusJogo) {
        this.statusJogo = statusJogo;
    }
}
