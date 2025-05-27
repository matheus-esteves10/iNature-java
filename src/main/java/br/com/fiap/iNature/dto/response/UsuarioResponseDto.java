package br.com.fiap.iNature.dto.response;

import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.model.enums.Role;

public record UsuarioResponseDto(
        Long id,
        String nome,
        String email,
        Role role
) {
    public static UsuarioResponseDto from(Usuario usuario) {
        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
}