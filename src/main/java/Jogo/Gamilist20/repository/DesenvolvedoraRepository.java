package Jogo.Gamilist20.repository;

import Jogo.Gamilist20.Modelo.Desenvolvedora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DesenvolvedoraRepository extends JpaRepository<Desenvolvedora, Long> {
    Optional<Desenvolvedora> findByNome(String nome);

}
