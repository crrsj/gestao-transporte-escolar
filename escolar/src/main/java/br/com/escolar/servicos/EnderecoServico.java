package br.com.escolar.servicos;

import br.com.escolar.config.ViaCepConfig;
import br.com.escolar.dtos.EnderecoDTO;
import br.com.escolar.entidades.Endereco;
import br.com.escolar.excessoes.EnderecoNaoEncontrado;
import br.com.escolar.excessoes.ResponsavelNaoEncontrado;
import br.com.escolar.repositorios.EnderecoRepositorio;
import br.com.escolar.repositorios.ResponsavelRepositorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoServico {

    private final EnderecoRepositorio enderecoRepositorio;
    private final ViaCepConfig viaCepConfig;
    private final ModelMapper modelMapper;
    private final ResponsavelRepositorio responsavelRepositorio;

    @Transactional
    public EnderecoDTO salvarEndereco(Long responsavelId, EnderecoDTO enderecoDTO){
       var responsavel = responsavelRepositorio.findById(responsavelId)
        .orElseThrow(()->new ResponsavelNaoEncontrado("Responsável não encontrado com o id: " + responsavelId));
        var dadosEndereco = viaCepConfig.buscarEnderecoPor(enderecoDTO.getCep());
        enderecoDTO.setLogradouro(dadosEndereco.getLogradouro());
        enderecoDTO.setBairro(dadosEndereco.getBairro());
        enderecoDTO.setLocalidade(dadosEndereco.getLocalidade());
        enderecoDTO.setUf(dadosEndereco.getUf());
        enderecoDTO.setEstado(dadosEndereco.getEstado());
        enderecoDTO.setResponsavel(responsavel);
        var endereco = modelMapper.map(enderecoDTO,Endereco.class);
        var novoEndereco = enderecoRepositorio.save(endereco);
        return modelMapper.map(novoEndereco, EnderecoDTO.class);



    }

    public Page<EnderecoDTO>listarEnderecos(Pageable pageable){
        return enderecoRepositorio.findAll(pageable)
                .map(enderecos->modelMapper.map(enderecos,EnderecoDTO.class));
    }

    private Endereco buscarEnderecoOuLancarExcessao(Long id){
        return enderecoRepositorio.findById(id).orElseThrow(
                ()-> new EnderecoNaoEncontrado("Endereço não encontrado com o id: " + id ));
    }


    @Transactional
    public EnderecoDTO atualizarEndereco(Long id, EnderecoDTO enderecoDTO){
       var endereco = buscarEnderecoOuLancarExcessao(id);
        modelMapper.map(enderecoDTO,endereco);
        return modelMapper.map(endereco, EnderecoDTO.class);
    }

    public EnderecoDTO buscarEnderecoPorId(Long id){
        var endereco = buscarEnderecoOuLancarExcessao(id);
        return modelMapper.map(endereco, EnderecoDTO.class);
    }

    @Transactional
    public void excluirEndereco(Long id){
        var endereco = buscarEnderecoOuLancarExcessao(id);
        enderecoRepositorio.delete(endereco);
    }
}
