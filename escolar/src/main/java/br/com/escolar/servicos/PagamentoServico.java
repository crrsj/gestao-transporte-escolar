package br.com.escolar.servicos;

import br.com.escolar.dtos.PagamentoDTO;
import br.com.escolar.entidades.Pagamento;
import br.com.escolar.excessoes.PagamentoNaoEncontrado;
import br.com.escolar.excessoes.ResponsavelNaoEncontrado;
import br.com.escolar.repositorios.PagamentoRepositorio;
import br.com.escolar.repositorios.ResponsavelRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PagamentoServico {

    private final ModelMapper modelMapper;
    private final PagamentoRepositorio pagamentoRepositorio;
    private final ResponsavelRepositorio responsavelRepositorio;


    @Transactional
    public PagamentoDTO salvarPagamento(Long responsavelId,PagamentoDTO pagamentoDTO){
        var responsavel = responsavelRepositorio.findById(responsavelId)
                .orElseThrow(()->new ResponsavelNaoEncontrado("Reponsável não encontrado."));
        var pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setDataPagamento(LocalDate.now());
        pagamento.setResponsavel(responsavel);
        var novoPagamento = pagamentoRepositorio.save(pagamento);
        return modelMapper.map(novoPagamento, PagamentoDTO.class);
    }

    private Pagamento buscarPagamentoOuLancarExcessao(Long id){
        return pagamentoRepositorio.findById(id).orElseThrow(()->new PagamentoNaoEncontrado("Pagamento não encontrado."));
    }

    public Page<PagamentoDTO>listarPagamentos(Pageable pageable){
        return pagamentoRepositorio.findAll(pageable).map(pagamentos -> modelMapper.map(pagamentos, PagamentoDTO.class));
    }


    public PagamentoDTO buscarPagamentoPorId(Long id){
        var pagamento = buscarPagamentoOuLancarExcessao(id);
        return modelMapper.map(pagamento,PagamentoDTO.class);
    }

    @Transactional
    public PagamentoDTO atualizarPagamentos(Long id, PagamentoDTO pagamentoDTO){
        var pagamento = buscarPagamentoOuLancarExcessao(id);
        modelMapper.map(pagamentoDTO,pagamento);
        var atualizado = pagamentoRepositorio.save(pagamento);
        return modelMapper.map(atualizado, PagamentoDTO.class);
    }

    @Transactional
    public void excluirPagamento(Long id){
        var pagamento = buscarPagamentoOuLancarExcessao(id);
        pagamentoRepositorio.delete(pagamento);
    }
}
