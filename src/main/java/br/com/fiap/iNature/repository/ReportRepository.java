package br.com.fiap.iNature.repository;

import br.com.fiap.iNature.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
