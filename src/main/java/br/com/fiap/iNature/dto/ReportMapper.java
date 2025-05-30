package br.com.fiap.iNature.dto;

import br.com.fiap.iNature.model.Localizacao;
import br.com.fiap.iNature.model.Report;
import br.com.fiap.iNature.model.Usuario;

import java.time.LocalDate;
import java.time.ZoneId;

public class ReportMapper {

    public static Localizacao toLocalizacao(LocalizacaoDto dto) {
        return Localizacao.builder()
                .cidade(dto.cidade())
                .bairro(dto.bairro())
                .logradouro(dto.logradouro())
                .numero(dto.numero())
                .build();
    }

    public static Report toReport(ReportDto dto, Usuario usuario, Localizacao localizacao) {
        Report report = new Report();
        report.setTitulo(dto.titulo());
        report.setCorpo(dto.corpo());
        report.setTipo(dto.tipoReport());
        report.setData(LocalDate.now(ZoneId.of("America/Sao_Paulo")));
        report.setLocalizacao(localizacao);
        report.setUsuario(usuario);
        return report;
    }
}

