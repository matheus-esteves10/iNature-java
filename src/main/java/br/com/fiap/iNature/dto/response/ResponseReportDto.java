package br.com.fiap.iNature.dto.response;

import br.com.fiap.iNature.dto.LocalizacaoDto;
import br.com.fiap.iNature.model.Report;
import br.com.fiap.iNature.model.enums.TipoReport;

import java.time.LocalDate;

public record ResponseReportDto(
        Long id,
        String titulo,
        String corpo,
        TipoReport tipo,
        LocalDate data,
        String usuarioNome,
        LocalizacaoDto localizacao,
        int quantidadeConfirmacoes,
        Boolean usuarioConfirmou
) {

    public static ResponseReportDto from(Report report, Long usuarioId) {
        Boolean usuarioConfirmou = null;

        if (usuarioId != null) {
            usuarioConfirmou = report.getConfirmacoes().stream()
                    .anyMatch(c -> c.getUsuario().getId().equals(usuarioId));
        }

        return new ResponseReportDto(
                report.getId(),
                report.getTitulo(),
                report.getCorpo(),
                report.getTipo(),
                report.getData(),
                report.getUsuario().getNome(),
                LocalizacaoDto.from(report.getLocalizacao()),
                report.getConfirmacoes().size(),
                usuarioConfirmou
        );
    }
}

