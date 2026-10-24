package br.com.escolar.servicos;


import br.com.escolar.dtos.ResponsavelDTO;
import br.com.escolar.dtos.ResponsavelDtoSemCpf;
import br.com.escolar.entidades.Responsavel;
import br.com.escolar.excessoes.ResponsavelNaoEncontrado;
import br.com.escolar.repositorios.ResponsavelRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponsavelServico {

    private final ResponsavelRepositorio responsavelRepositorio;
    private final ModelMapper modelMapper;

    @Transactional
    public ResponsavelDtoSemCpf salvarResponsavel(ResponsavelDTO responsavelDTO){
        var responsavel = modelMapper.map(responsavelDTO, Responsavel.class);
        var novoResponsavel = responsavelRepositorio.save(responsavel);
        return  modelMapper.map(novoResponsavel, ResponsavelDtoSemCpf.class);
    }

    public Page<ResponsavelDtoSemCpf>listarResponsaveis(Pageable pageable){
        return responsavelRepositorio.findAll(pageable)
                .map(responsaveis ->modelMapper.map(responsaveis,ResponsavelDtoSemCpf.class));
    }

    private Responsavel buscarOuLancarExcessao(Long id) {
        return responsavelRepositorio.findById(id).orElseThrow(
                ()-> new ResponsavelNaoEncontrado("Responsável não encontrado na base de dados com o id: " + id));
    }


    public ResponsavelDtoSemCpf buscarResponsavelPorId(Long id){
        var responsavel = buscarOuLancarExcessao(id);
        return modelMapper.map(responsavel,ResponsavelDtoSemCpf.class);
    }

    public ResponsavelDtoSemCpf buscarPorCpf(String cpf){
        var responsavel = responsavelRepositorio.findByCpf(cpf).orElseThrow(ResponsavelNaoEncontrado::new);
        return modelMapper.map(responsavel, ResponsavelDtoSemCpf.class);
    }

    @Transactional
    public ResponsavelDtoSemCpf atualizarResponsavel(Long id, ResponsavelDtoSemCpf responsavelDtoSemCpf){
        var responsavel = buscarOuLancarExcessao(id);
        modelMapper.map(responsavelDtoSemCpf,responsavel);
        var atualizado = responsavelRepositorio.save(responsavel);
        return modelMapper.map(atualizado, ResponsavelDtoSemCpf.class);
    }

    @Transactional
    public void excluirResponsavel(Long id){
        var responsavel = buscarOuLancarExcessao(id);
        responsavelRepositorio.delete(responsavel);
    }
}
