package br.com.escolar.excessoes;

public class PagamentoNaoEncontrado extends RuntimeException {
    public PagamentoNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
