package br.com.fiap.iNature.controller;

import br.com.fiap.iNature.dto.NoticiaDto;
import br.com.fiap.iNature.dto.response.NoticiaSelecionadaResponse;
import br.com.fiap.iNature.dto.response.NoticiaResponseDto;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.service.NoticiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/noticias")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @Operation(
            summary = "Criar uma nova notícia",
            description = "Cria uma notícia com imagem e os dados fornecidos. Apenas usuários com perfil JORNALISTA têm permissão."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notícia criada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário sem permissão para criar notícia"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @CacheEvict(value = "noticias", allEntries = true)
    public ResponseEntity<?> criar(@ModelAttribute NoticiaDto dto) throws IOException {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NoticiaResponseDto response = noticiaService.criarNoticia(dto, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    @Cacheable(value = "noticias")
    @Operation(
            summary = "Listar notícias",
            description = "Retorna uma página de notícias ordenadas por data de publicação. Pode ser filtrada por data (?dataInicio=2024-01-01&dataFim=2024-12-31)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notícias retornada com sucesso"),
    })
    public ResponseEntity<Page<NoticiaResponseDto>> listarNoticias(
            @RequestParam(value = "dataInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(value = "dataFim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            Pageable pageable
    ) {
        Page<NoticiaResponseDto> noticias = noticiaService.listarNoticias(dataInicio, dataFim, pageable);
        return ResponseEntity.ok(noticias);
    }

    @Operation(
            summary = "Buscar notícia por ID",
            description = "Retorna os detalhes completos de uma notícia com base no ID informado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notícia encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Notícia não encontrada"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoticiaSelecionadaResponse> buscarPorId(@PathVariable Long id) {
        NoticiaSelecionadaResponse dto = noticiaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

}
