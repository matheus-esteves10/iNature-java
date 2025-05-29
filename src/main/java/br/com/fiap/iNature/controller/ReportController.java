package br.com.fiap.iNature.controller;

import br.com.fiap.iNature.dto.ReportDto;
import br.com.fiap.iNature.dto.response.ResponseReportDto;
import br.com.fiap.iNature.model.Report;
import br.com.fiap.iNature.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping
    @Operation(
            summary = "Criar novo report",
            description = "Cria um novo report associado ao usuário autenticado.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Report criado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Token inválido ou ausente", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<ResponseReportDto> criarReport(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid ReportDto dto
    ) {

        Report report = reportService.criarReport(token, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseReportDto.from(report));
    }

    @PostMapping("/{id}/confirmacao")
    public ResponseEntity<?> confirmarReport(@RequestHeader("Authorization") String token,
                                             @PathVariable("id") Long reportId) {

        reportService.confirmarReport(token, reportId);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{id}/remocao")
    public ResponseEntity<Void> removerConfirmacao(@RequestHeader("Authorization") String token,
                                                   @PathVariable Long id) {
        reportService.removerConfirmacaoReport(token, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/confirmacoes/{id}")
    public ResponseEntity<Map<String, Long>> getQuantidadeConfirmacoes(@PathVariable("id") Long reportId) {

        long total = reportService.getQuantidadeConfirmacoes(reportId);
        return ResponseEntity.ok(Map.of("quantidadeConfirmacoes", total));
    }

    @GetMapping("/hoje")
    public ResponseEntity<Page<ResponseReportDto>> listarReportsDeHoje(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "12") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseReportDto> reports = reportService.getReportsDoDiaMaisConfirmados(pageable);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/bairro")
    public ResponseEntity<Page<ResponseReportDto>> listarReportsPorBairro(
            @RequestParam String bairro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseReportDto> reports = reportService.getReportsPorBairro(bairro, pageable);
        return ResponseEntity.ok(reports);
    }

}
