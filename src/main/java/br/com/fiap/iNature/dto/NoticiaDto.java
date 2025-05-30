package br.com.fiap.iNature.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record NoticiaDto(String titulo,
                         String resumo,
                         String corpo,
                         MultipartFile imagem
                         ) {
}
