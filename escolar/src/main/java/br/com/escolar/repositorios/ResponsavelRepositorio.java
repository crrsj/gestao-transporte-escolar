package br.com.escolar.repositorios;

import br.com.escolar.entidades.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsavelRepositorio extends JpaRepository<Responsavel,Long> {
    Optional<Responsavel> findByCpf(String cpf);
}
