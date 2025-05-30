package br.com.fiap.iNature.exceptions;

public class UserNotAuthException extends RuntimeException {
    public UserNotAuthException(String message) {
        super(message);
    }
}
