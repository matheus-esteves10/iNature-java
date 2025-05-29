package br.com.fiap.iNature.dto;

import br.com.fiap.iNature.model.enums.TipoReport;

public record ReportDto(
        String titulo,
        String corpo,
        TipoReport tipoReport,
        LocalizacaoDto localizacao
) {
}
