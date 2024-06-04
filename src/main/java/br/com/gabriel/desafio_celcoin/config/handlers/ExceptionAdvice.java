package br.com.gabriel.desafio_celcoin.config.handlers;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;


@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> error404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> error400byvalidation(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getFieldErrors().stream().map(x -> {
            String message = x.getDefaultMessage();
            return new ValidError(x.getField(), switch (message) {
                case "must not be blank", "não deve estar em branco" -> "O campo não pode ser vazio";
                case "must not be null", "não deve ser nulo" -> "O campo não pode ser nulo";
                default -> x.getDefaultMessage();
            });
        }).collect(Collectors.toList()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> error400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> error500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + ex.getLocalizedMessage());
    }

    private record ValidError(String field, String message) {}
}