package br.com.fiap.iNature.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record NoticiaResponseDto(
        Long id,
        String titulo,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataPublicacao,
        String resumo,
        String imagemCapa,
        String autor
) {
}

