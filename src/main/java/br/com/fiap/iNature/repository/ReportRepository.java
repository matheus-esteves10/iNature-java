package br.com.fiap.iNature.repository;

import br.com.fiap.iNature.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("""
    SELECT r FROM Report r
    WHERE r.data = :data
    ORDER BY SIZE(r.confirmacoes) DESC
""")
    Page<Report> findReportsDoDiaOrderByConfirmacoesDesc(@Param("data") LocalDate data, Pageable pageable);

    @Query("""
    SELECT r FROM Report r
    WHERE LOWER(r.localizacao.bairro) LIKE LOWER(CONCAT('%', :bairro, '%'))
    ORDER BY r.data DESC
""")
    Page<Report> findByBairroContainingIgnoreCaseOrderByDataDesc(@Param("bairro") String bairro, Pageable pageable);



}
