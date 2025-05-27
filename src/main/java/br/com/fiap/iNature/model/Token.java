package br.com.fiap.iNature.model;

import br.com.fiap.iNature.model.enums.Role;

public record Token(
        Long idUser,
        String nome,
        Role role
) {
}
