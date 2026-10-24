package br.com.escolar.repositorios;

import br.com.escolar.entidades.Escola;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EscolaRepositorio extends JpaRepository<Escola,Long> {
}
