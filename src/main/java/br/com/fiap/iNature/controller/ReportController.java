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

    @GetMapping("/confirmacoes/{id}")
    public ResponseEntity<Map<String, Long>> getQuantidadeConfirmacoes(@PathVariable("id") Long reportId) {

        long total = reportService.getQuantidadeConfirmacoes(reportId);
        return ResponseEntity.ok(Map.of("quantidadeConfirmacoes", total));
    }


}
