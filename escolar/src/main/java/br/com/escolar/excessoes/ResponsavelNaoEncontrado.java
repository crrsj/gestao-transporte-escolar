package br.com.escolar.excessoes;

public class ResponsavelNaoEncontrado extends RuntimeException {
    public ResponsavelNaoEncontrado(String mensagem) {
        super(mensagem);
    }
    public ResponsavelNaoEncontrado(){
        super();
    }
}
