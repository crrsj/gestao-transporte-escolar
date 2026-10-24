package br.com.escolar.excessoes;

import org.springframework.validation.FieldError;

public record CamposValidos(String campo, String mensagem) {
    public CamposValidos(FieldError erros){
        this(erros.getField(),erros.getDefaultMessage());
    }
}
