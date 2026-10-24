package br.com.escolar.repositorios;

import br.com.escolar.entidades.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepositorio extends JpaRepository<Endereco,Long> {
}
