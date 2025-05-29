package br.com.fiap.iNature.service;


import br.com.fiap.iNature.dto.ReportDto;
import br.com.fiap.iNature.model.Localizacao;
import br.com.fiap.iNature.model.Report;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.repository.LocalizacaoRepository;
import br.com.fiap.iNature.repository.ReportRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public Report criarReport(String token, ReportDto dto) {

        Usuario usuario = tokenService.getUsuarioLogado(token);

        var local = Localizacao.builder()
                .cidade(dto.localizacao().cidade())
                .bairro(dto.localizacao().bairro())
                .logradouro(dto.localizacao().logradouro())
                .numero(dto.localizacao().numero())
                .build();

        localizacaoRepository.save(local);

        Report report = new Report();
        report.setTitulo(dto.titulo());
        report.setCorpo(dto.corpo());
        report.setTipo(dto.tipoReport());
        report.setData(LocalDate.now(ZoneId.of("America/Sao_Paulo")));
        report.setLocalizacao(local);
        report.setUsuario(usuario);
        return reportRepository.save(report);

    }
}
