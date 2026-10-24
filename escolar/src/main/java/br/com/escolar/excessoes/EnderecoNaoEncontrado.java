package br.com.escolar.excessoes;

public class EnderecoNaoEncontrado extends RuntimeException {
    public EnderecoNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
