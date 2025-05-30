package br.com.fiap.iNature.controller;

import br.com.fiap.iNature.dto.NoticiaDto;
import br.com.fiap.iNature.dto.response.NoticiaSelecionadaResponse;
import br.com.fiap.iNature.dto.response.NoticiaResponseDto;
import br.com.fiap.iNature.service.NoticiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
    public ResponseEntity<?> criar(@ModelAttribute NoticiaDto dto) throws IOException {

        NoticiaResponseDto response = noticiaService.criarNoticia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @Operation(
            summary = "Listar notícias",
            description = "Retorna uma página de notícias ordenadas por data de publicação. (?page=0&size=10&sort=dataPublicacao,desc)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de notícias retornada com sucesso"),
    })
    @GetMapping
    public ResponseEntity<Page<NoticiaResponseDto>> listarNoticias(
            Pageable pageable
    ) {
        Page<NoticiaResponseDto> noticias = noticiaService.listarNoticias(pageable);
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
