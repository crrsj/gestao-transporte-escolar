package br.com.escolar.repositorios;

import br.com.escolar.entidades.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepositorio extends JpaRepository<Aluno,Long> {
}
