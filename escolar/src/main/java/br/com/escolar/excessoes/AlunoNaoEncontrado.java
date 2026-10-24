package br.com.escolar.excessoes;

public class AlunoNaoEncontrado extends RuntimeException{
    public AlunoNaoEncontrado(String mensagem) {
       super(mensagem);
  }
}
