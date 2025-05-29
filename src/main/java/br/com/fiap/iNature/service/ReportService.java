package br.com.fiap.iNature.service;

import br.com.fiap.iNature.dto.ReportDto;
import br.com.fiap.iNature.exceptions.ReportAlreadyConfirmedException;
import br.com.fiap.iNature.exceptions.ReportNotFoundException;
import br.com.fiap.iNature.model.*;
import br.com.fiap.iNature.repository.ConfirmacaoRepository;
import br.com.fiap.iNature.repository.LocalizacaoRepository;
import br.com.fiap.iNature.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public void confirmarReport(String token, Long reportId) {
        Usuario usuario = tokenService.getUsuarioLogado(token);

        Report report = reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);

        // Verifica se já confirmou
        Optional<ConfirmacaoReport> confirmacaoExistente = confirmacaoRepository.findById(
                new ConfirmacaoReportId(reportId, usuario.getId())
        );

        if (confirmacaoExistente.isPresent()) {
            throw new ReportAlreadyConfirmedException();
        }

        criaConfirmacaoReport(new ConfirmacaoReportId(reportId, usuario.getId()), report, usuario);
    }


    @Transactional
    public void removerConfirmacaoReport(String token, Long reportId) {
        Usuario usuario = tokenService.getUsuarioLogado(token);

        ConfirmacaoReportId confirmacaoId = new ConfirmacaoReportId(reportId, usuario.getId());

        ConfirmacaoReport confirmacao = confirmacaoRepository.findById(confirmacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Confirmação não encontrada para esse usuário e report"));

        confirmacaoRepository.delete(confirmacao);
    }

    public long getQuantidadeConfirmacoes(Long reportId) {
        if (!reportRepository.existsById(reportId)) {
            throw new EntityNotFoundException("Report não encontrado com id: " + reportId);
        }

        return confirmacaoRepository.countByReportId(reportId);
    }

    private void criaConfirmacaoReport(ConfirmacaoReportId reportId, Report report, Usuario usuario) {
        ConfirmacaoReport confirmacao = new ConfirmacaoReport();
        confirmacao.setId(reportId);
        confirmacao.setReport(report);
        confirmacao.setUsuario(usuario);
        confirmacao.setDataConfirmacao(LocalDate.now());

        confirmacaoRepository.save(confirmacao);
    }

}
