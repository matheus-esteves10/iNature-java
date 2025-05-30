package br.com.fiap.iNature.service;

import br.com.fiap.iNature.dto.ReportDto;
import br.com.fiap.iNature.dto.ReportMapper;
import br.com.fiap.iNature.dto.response.ResponseReportDto;
import br.com.fiap.iNature.exceptions.ConfirmacaoNotFoundException;
import br.com.fiap.iNature.exceptions.ReportAlreadyConfirmedException;
import br.com.fiap.iNature.exceptions.ReportNotFoundException;
import br.com.fiap.iNature.model.*;
import br.com.fiap.iNature.repository.ConfirmacaoRepository;
import br.com.fiap.iNature.repository.LocalizacaoRepository;
import br.com.fiap.iNature.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private LocalizacaoRepository localizacaoRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ConfirmacaoRepository confirmacaoRepository;

    @Transactional
    public Report criarReport(String token, ReportDto dto) {
        Usuario usuario = tokenService.getUsuarioLogado(token);

        var local = ReportMapper.toLocalizacao(dto.localizacao());

        localizacaoRepository.save(local);

        var report = ReportMapper.toReport(dto, usuario, local);

        return reportRepository.save(report);
    }

    @Transactional
    public void confirmarReport(String token, Long reportId) {
        Usuario usuario = getUsuarioLogado(token);
        Report report = getReportOrThrow(reportId);

        ConfirmacaoReportId confirmacaoId = new ConfirmacaoReportId(reportId, usuario.getId());

        if (confirmacaoRepository.existsById(confirmacaoId)) {
            throw new ReportAlreadyConfirmedException();
        }

        criaConfirmacaoReport(confirmacaoId, report, usuario);
    }

    @Transactional
    public void removerConfirmacaoReport(String token, Long reportId) {
        ConfirmacaoReportId confirmacaoId = getConfirmacaoId(token, reportId);

        ConfirmacaoReport confirmacao = confirmacaoRepository.findById(confirmacaoId)
                .orElseThrow(ConfirmacaoNotFoundException::new);

        confirmacaoRepository.delete(confirmacao);
    }

    public long getQuantidadeConfirmacoes(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new ReportNotFoundException(reportId);
        }

        return confirmacaoRepository.countByReportId(reportId);
    }

    public Page<ResponseReportDto> getReportsDoDiaMaisConfirmados(Pageable pageable) {
        LocalDate hoje = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        Page<Report> reports = reportRepository.findReportsDoDiaOrderByConfirmacoesDesc(hoje, pageable);
        return reports.map(ResponseReportDto::from);
    }

    public Page<ResponseReportDto> getReportsPorBairro(String bairro, Pageable pageable) {
        Page<Report> reports = reportRepository.findByBairroContainingIgnoreCaseOrderByDataDesc(bairro, pageable);
        return reports.map(ResponseReportDto::from);
    }

    // Auxiliares

    private void criaConfirmacaoReport(ConfirmacaoReportId reportId, Report report, Usuario usuario) {
        ConfirmacaoReport confirmacao = new ConfirmacaoReport();
        confirmacao.setId(reportId);
        confirmacao.setReport(report);
        confirmacao.setUsuario(usuario);
        confirmacao.setDataConfirmacao(LocalDate.now());

        confirmacaoRepository.save(confirmacao);
    }

    private Usuario getUsuarioLogado(String token) {
        return tokenService.getUsuarioLogado(token);
    }

    private Report getReportOrThrow(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new ReportNotFoundException(id));
    }

    private ConfirmacaoReportId getConfirmacaoId(String token, Long reportId) {
        Usuario usuario = getUsuarioLogado(token);
        return new ConfirmacaoReportId(reportId, usuario.getId());
    }

}



