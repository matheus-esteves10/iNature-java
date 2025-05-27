package br.com.fiap.iNature.dto;

import br.com.fiap.iNature.model.enums.Role;

public record Token(
        Long idUser,
        String nome,
        Role role
) {
}
