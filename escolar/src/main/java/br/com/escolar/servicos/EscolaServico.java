package br.com.escolar.servicos;

import br.com.escolar.dtos.EscolaDTO;
import br.com.escolar.entidades.Escola;
import br.com.escolar.excessoes.EscolaNaoEncontrada;
import br.com.escolar.repositorios.EscolaRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EscolaServico {

    private final EscolaRepositorio escolaRepositorio;
    private final ModelMapper modelMapper;

    @Transactional
    public EscolaDTO salvarEscola(EscolaDTO escolaDTO){
        var escola = modelMapper.map(escolaDTO, Escola.class);
        var novaEscola = escolaRepositorio.save(escola);
        return modelMapper.map(novaEscola, EscolaDTO.class);
    }

    public Page<EscolaDTO>listarEscolas(Pageable pageable){
        return escolaRepositorio.findAll(pageable)
                .map(escolas->modelMapper.map(escolas,EscolaDTO.class));


    }

    private Escola buscarEscolaOuLancarExcessao(Long id){
        return escolaRepositorio.findById(id)
                .orElseThrow(()->new EscolaNaoEncontrada("Escola não encontrada."));
    }

    public EscolaDTO buscarEscolaPorId(Long id){
        var escola = buscarEscolaOuLancarExcessao(id);
        return modelMapper.map(escola, EscolaDTO.class);
    }


    @Transactional
    public EscolaDTO atualizarEscola(Long id, EscolaDTO escolaDTO){
        var escola = buscarEscolaOuLancarExcessao(id);
        modelMapper.map(escolaDTO,escola);
        var atualizada = escolaRepositorio.save(escola);
        return modelMapper.map(atualizada, EscolaDTO.class);
    }

    @Transactional
    public void excluirEscola(Long id){
        var escola = buscarEscolaOuLancarExcessao(id);
        escolaRepositorio.delete(escola);
    }
}
