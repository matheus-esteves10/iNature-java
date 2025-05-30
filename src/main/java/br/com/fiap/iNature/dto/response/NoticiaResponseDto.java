package br.com.fiap.iNature.dto.response;

import java.time.LocalDate;

public record NoticiaResponseDto(Long id,
                                 String titulo,
                                 LocalDate dataPublicacao,
                                 String resumo,
                                 String imagemCapa,
                                 String autor
) {
}
