package br.com.fiap.iNature.controller;

import br.com.fiap.iNature.dto.NoticiaDto;
import br.com.fiap.iNature.dto.response.NoticiaIdResponse;
import br.com.fiap.iNature.dto.response.NoticiaResponseDto;
import br.com.fiap.iNature.service.NoticiaService;
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

    @PostMapping
    public ResponseEntity<?> criar(@ModelAttribute NoticiaDto dto,
                                   @RequestHeader("Authorization") String authorizationHeader) throws IOException {

        String token = authorizationHeader.replace("Bearer ", "");
        NoticiaResponseDto response = noticiaService.criarNoticia(token, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping
    public ResponseEntity<Page<NoticiaResponseDto>> listarNoticias(
            Pageable pageable
    ) {
        Page<NoticiaResponseDto> noticias = noticiaService.listarNoticias(pageable);
        return ResponseEntity.ok(noticias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoticiaIdResponse> buscarPorId(@PathVariable Long id) {
        NoticiaIdResponse dto = noticiaService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

}
