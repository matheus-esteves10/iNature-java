package br.com.fiap.iNature.exceptions;

public class ReportNotFoundException extends RuntimeException{

    public ReportNotFoundException(Long id) {
        super("Report nao encontrado com id: " + id);

    }
}
