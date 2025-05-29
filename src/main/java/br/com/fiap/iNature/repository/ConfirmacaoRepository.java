package br.com.fiap.iNature.repository;

import br.com.fiap.iNature.model.ConfirmacaoReport;
import br.com.fiap.iNature.model.ConfirmacaoReportId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmacaoRepository extends JpaRepository<ConfirmacaoReport, ConfirmacaoReportId> {

    long countByReportId(Long reportId);
}
