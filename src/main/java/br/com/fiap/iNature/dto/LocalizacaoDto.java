package br.com.fiap.iNature.dto;

import br.com.fiap.iNature.model.Localizacao;

public record LocalizacaoDto(
        String cidade,
        String bairro,
        String logradouro,
        String numero
) {
    public static LocalizacaoDto from(Localizacao local) {
        return new LocalizacaoDto(
                local.getCidade(),
                local.getBairro(),
                local.getLogradouro(),
                local.getNumero()
        );
    }
}

