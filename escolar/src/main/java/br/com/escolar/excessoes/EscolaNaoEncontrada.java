package br.com.escolar.excessoes;

public class EscolaNaoEncontrada extends RuntimeException {
    public EscolaNaoEncontrada(String mensagem) {
        super(mensagem);
    }
}
