package br.com.fiap.iNature.controller;

import br.com.fiap.iNature.dto.UsuarioDto;
import br.com.fiap.iNature.dto.response.UsuarioResponseDto;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Cadastrar novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operador criado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json")),
            })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioDto dto) {
        Usuario usuario = usuarioService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponseDto.from(usuario));
    }

    @Operation(
            summary = "Listar todos os usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários", content = @Content(mediaType = "application/json"))
            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listarTodos() {
        List<UsuarioResponseDto> lista = usuarioService.listarTodos()
                .stream()
                .map(UsuarioResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @Operation(
            summary = "Buscar usuário por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDto> buscarPorId() {
        Usuario usuario = usuarioService.buscarPorId();
        return ResponseEntity.ok(UsuarioResponseDto.from(usuario));
    }

    @Operation(summary = "Atualizar usuário logado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json"))})
    @PutMapping("/me")
    public ResponseEntity<UsuarioResponseDto> atualizarMeuPerfil(@RequestBody @Valid UsuarioDto dto) {
        Usuario usuario = usuarioService.atualizar(dto);
        return ResponseEntity.ok(UsuarioResponseDto.from(usuario));
    }


    @Operation(summary = "Deletar usuário logado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Usuário nao encontrado", content = @Content(mediaType = "application/json"))
            })
    @DeleteMapping("/me")
    public ResponseEntity<Void> deletarMeuPerfil() {
        usuarioService.deletar();
        return ResponseEntity.noContent().build();
    }

}
