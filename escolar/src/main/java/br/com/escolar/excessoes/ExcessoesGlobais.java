package br.com.escolar.excessoes;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExcessoesGlobais {

    @ExceptionHandler(ResponsavelNaoEncontrado.class)
    public ResponseEntity<MensagemDeErro>responsavelNaoEncontrado(){
        var erros = new MensagemDeErro(HttpStatus.NOT_FOUND,"Responsável não encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erros);
    }

    @ExceptionHandler(AlunoNaoEncontrado.class)
    public ResponseEntity<MensagemDeErro>alunoNaoEncontrado(){
        var erros = new MensagemDeErro(HttpStatus.NOT_FOUND,"Aluno não encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erros);
    }

    @ExceptionHandler(PagamentoNaoEncontrado.class)
    public ResponseEntity<MensagemDeErro>pagamentoNaoEncontrado(){
        var erros = new MensagemDeErro(HttpStatus.NOT_FOUND,"Pagamento não encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erros);
    }

    @ExceptionHandler(EnderecoNaoEncontrado.class)
    public ResponseEntity<MensagemDeErro>enderecoNaoEncontrado(){
        var erros = new MensagemDeErro(HttpStatus.NOT_FOUND,"Endereço não encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erros);
    }

    public ResponseEntity<?>validandoCampos(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros.stream().map(CamposValidos::new).toList());
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MensagemDeErro>cpfUnico(){
        var erros = new MensagemDeErro(HttpStatus.CONFLICT,"CPF Já existe na base de dados.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erros);
    }

}
