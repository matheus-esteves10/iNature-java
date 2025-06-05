package br.com.fiap.iNature.controller;

import br.com.fiap.iNature.dto.ReportDto;
import br.com.fiap.iNature.dto.response.ResponseReportDto;
import br.com.fiap.iNature.model.Report;
import br.com.fiap.iNature.service.ReportService;
import br.com.fiap.iNature.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Autowired
    private TokenService tokenService;

    @Operation(
            summary = "Criar novo report",
            description = "Cria um novo report associado ao usuário autenticado.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Report criado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Token inválido ou ausente", content = @Content(mediaType = "application/json"))
            }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ResponseReportDto> criarReport(@RequestBody @Valid ReportDto dto) {

        Report report = reportService.criarReport(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseReportDto.from(report, tokenService.getUsuarioLogado().getId()));
    }

    @Operation(
            summary = "Confirmar um report",
            description = "Confirma um report pelo ID. O usuário precisa estar autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Report não encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuário já confirmou este report"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{id}/confirmacao")
    public ResponseEntity<?> confirmarReport(@PathVariable("id") Long reportId) {

        reportService.confirmarReport(reportId);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Operation(
            summary = "Remover confirmação de report",
            description = "Remove a confirmação de um report feita pelo usuário autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Confirmação removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Confirmação não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}/remocao")
    public ResponseEntity<Void> removerConfirmacao(@PathVariable Long id) {
        reportService.removerConfirmacaoReport(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Obter quantidade de confirmações de um report",
            description = "Retorna o número total de confirmações associadas a um report específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Report não encontrado")
    })
    @GetMapping("/confirmacoes/{id}")
    public ResponseEntity<Map<String, Long>> getQuantidadeConfirmacoes(@PathVariable("id") Long reportId) {

        long total = reportService.getQuantidadeConfirmacoes(reportId);
        return ResponseEntity.ok(Map.of("quantidadeConfirmacoes", total));
    }


    @Operation(
            summary = "Listar reports do dia com mais confirmações",
            description = "Retorna os reports do dia atual, ordenados por quantidade de confirmações em ordem decrescente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reports retornada com sucesso")
    })
    @GetMapping("/hoje")
    public ResponseEntity<Page<ResponseReportDto>> listarReportsDeHoje(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "12") int size,
                                                                       @RequestHeader(value = "Authorization", required = false) String token) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseReportDto> reports = reportService.getReportsDoDiaMaisConfirmados(pageable, token);
        return ResponseEntity.ok(reports);
    }

    @Operation(
            summary = "Listar reports por bairro",
            description = "Retorna os reports filtrados por bairro, ordenados por data de publicação."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de reports retornada com sucesso")
    })
    @GetMapping("/bairro")
    public ResponseEntity<Page<ResponseReportDto>> listarReportsPorBairro(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(value = "Authorization", required = false) String token) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ResponseReportDto> reports = reportService.getReportsPorBairro(nome, pageable, token);
        return ResponseEntity.ok(reports);
    }

}
