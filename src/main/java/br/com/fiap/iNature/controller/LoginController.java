package br.com.fiap.iNature.controller;

import br.com.fiap.iNature.dto.Credentials;
import br.com.fiap.iNature.dto.Token;
import br.com.fiap.iNature.dto.response.LoginResponse;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.service.AuthService;
import br.com.fiap.iNature.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Endpoint para autenticação e geração de token JWT")
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Autenticar usuário", description = "Retorna um token JWT válido se as credenciais estiverem corretas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token JWT", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Username não encontrado", content = @Content(mediaType = "application/json"))
            })
    public LoginResponse login(@RequestBody @Valid Credentials credentials){
        Usuario user = (Usuario) authService.loadUserByUsername(credentials.username());

        if (!passwordEncoder.matches(credentials.password(), user.getSenha())) {
            throw new BadCredentialsException("Senha incorreta");
        }

        String jwt = tokenService.generateJwtToken(user);

        return new LoginResponse(jwt);
    }

}
