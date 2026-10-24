package br.com.escolar.servicos;

import br.com.escolar.dtos.AlunoDTO;
import br.com.escolar.excessoes.AlunoNaoEncontrado;
import br.com.escolar.entidades.Aluno;
import br.com.escolar.excessoes.EscolaNaoEncontrada;
import br.com.escolar.excessoes.ResponsavelNaoEncontrado;
import br.com.escolar.repositorios.AlunoRepositorio;
import br.com.escolar.repositorios.EscolaRepositorio;
import br.com.escolar.repositorios.ResponsavelRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoServico {

    private final AlunoRepositorio alunoRepositorio;
    private final ResponsavelRepositorio responsavelRepositorio;
    private final ModelMapper modelMapper;
    private final EscolaRepositorio escolaRepositorio;

    @Transactional
    public AlunoDTO salvarAluno(Long responsavelId,Long escolaId,AlunoDTO alunoDTO){
        var responsavel = responsavelRepositorio.findById(responsavelId)
                .orElseThrow(()->new ResponsavelNaoEncontrado("Reponsável não encontrado."));
        var escola = escolaRepositorio.findById(escolaId).orElseThrow(()->new EscolaNaoEncontrada("Escola não encontrada."));
        var aluno = modelMapper.map(alunoDTO, Aluno.class);
        aluno.setResponsavel(responsavel);
        aluno.setEscola(escola);
        var novoAluno = alunoRepositorio.save(aluno);
        return modelMapper.map(novoAluno, AlunoDTO.class);
    }

    public Page<AlunoDTO>listarAlunos(Pageable pageable){
        return alunoRepositorio.findAll(pageable).map(alunos -> modelMapper.map(alunos, AlunoDTO.class));
    }

    private Aluno buscarAlunoOuLancarExcessao(Long id){
        return alunoRepositorio.findById(id).orElseThrow(()->new AlunoNaoEncontrado("Aluno não encontrado."));
    }


    public AlunoDTO buscarAlunoPorId(Long id){
        var aluno = buscarAlunoOuLancarExcessao(id);
        return modelMapper.map(aluno, AlunoDTO.class);
    }

    @Transactional
    public AlunoDTO atualizarAluno(Long id, AlunoDTO alunoDTO){
        var aluno = buscarAlunoOuLancarExcessao(id);
        modelMapper.map(alunoDTO,aluno);
        var atualizado = alunoRepositorio.save(aluno);
        return modelMapper.map(atualizado, AlunoDTO.class);
    }

    @Transactional
    public void excluirAluno(Long id){
        var aluno = buscarAlunoOuLancarExcessao(id);
        alunoRepositorio.delete(aluno);
    }
}
