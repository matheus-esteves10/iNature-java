package br.com.fiap.iNature.infra.exception;

import br.com.fiap.iNature.exceptions.AcessoNegadoException;
import br.com.fiap.iNature.exceptions.UsuarioNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ValidationHandler {

    record ValidationError(String field, String message) {
        public ValidationError(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handle(MethodArgumentNotValidException e){
        return e.getFieldErrors()
                .stream()
                .map(ValidationError::new)
                .toList();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Violação de integridade de dados");
        error.put("message", e.getLocalizedMessage());
        return error;
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> usuarioNotFound(UsuarioNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Operador informado não existe");
        error.put("message", e.getMessage());
        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errors.put(field, message);
        }

        return errors;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleBadCredentials(BadCredentialsException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Credenciais inválidas");
        error.put("message", e.getMessage());
        return error;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> usarnameNotFound(UsernameNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Usuário não encontrado");
        error.put("message", e.getMessage());
        return error;
    }

    @ExceptionHandler(AcessoNegadoException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> handleAcessoNegado(AcessoNegadoException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Acesso negado");
        error.put("message", e.getMessage());
        return error;
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> EnumInválido(HttpMessageNotReadableException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Erro de desserialização");
        error.put("message", e.getMessage());
        return error;
    }

}
