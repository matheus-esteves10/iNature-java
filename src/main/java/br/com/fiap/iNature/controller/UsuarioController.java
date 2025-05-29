package br.com.fiap.iNature.controller;

import br.com.fiap.iNature.dto.UsuarioDto;
import br.com.fiap.iNature.dto.response.UsuarioResponseDto;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping
    @Operation(summary = "Cadastrar novo usuário",
            description = "Cria um novo usuário com as informações fornecidas.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operador criado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(mediaType = "application/json")),
            })
    public ResponseEntity<UsuarioResponseDto> criar(@RequestBody @Valid UsuarioDto dto) {
        Usuario usuario = usuarioService.salvar(dto);
        return ResponseEntity.ok(UsuarioResponseDto.from(usuario));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os usuários",
            description = "Lista todos os usuários.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários", content = @Content(mediaType = "application/json"))
            })

    public ResponseEntity<List<UsuarioResponseDto>> listarTodos() {
        List<UsuarioResponseDto> lista = usuarioService.listarTodos()
                .stream()
                .map(UsuarioResponseDto::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Busca um usuário por ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@RequestHeader("Authorization") String authorizationHeader) {
        Usuario usuario = usuarioService.buscarPorId(authorizationHeader);
        return ResponseEntity.ok(UsuarioResponseDto.from(usuario));
    }

    @PutMapping("/me")
    @Operation(summary = "Atualizar usuário logado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json"))})
    public ResponseEntity<UsuarioResponseDto> atualizarMeuPerfil(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid UsuarioDto dto) {
        Usuario usuario = usuarioService.atualizar(authorizationHeader, dto);
        return ResponseEntity.ok(UsuarioResponseDto.from(usuario));
    }

    @DeleteMapping("/me")
    @Operation(summary = "Deletar usuário logado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Usuário nao encontrado", content = @Content(mediaType = "application/json"))
            })
    public ResponseEntity<Void> deletarMeuPerfil(@RequestHeader("Authorization") String authorizationHeader) {
        usuarioService.deletar(authorizationHeader);
        return ResponseEntity.noContent().build();
    }

}
