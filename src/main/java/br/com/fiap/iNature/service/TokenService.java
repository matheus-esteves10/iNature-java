package br.com.fiap.iNature.service;

import br.com.fiap.iNature.dto.Token;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.model.enums.Role;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


import com.auth0.jwt.JWT;

@Service
public class TokenService {

    private static final String SECRET = "secret";

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    // Cria o token JWT e retorna o DTO Token (idUser, nome, role)
    public Token createToken(Usuario user) {
        Instant expiresAt = LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.ofHours(-3));

        var jwt = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("nome", user.getNome())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);

        return new Token(user.getId(), user.getNome(), user.getRole());
    }

    public Usuario getUserFromToken(String jwt) {
        var jwtVerified = JWT.require(algorithm).build().verify(jwt);

        Long id = Long.valueOf(jwtVerified.getSubject());
        String nome = jwtVerified.getClaim("nome").asString();
        String roleStr = jwtVerified.getClaim("role").asString();
        Role role = Role.valueOf(roleStr);

        Usuario user = new Usuario();
        user.setId(id);
        user.setNome(nome);
        user.setRole(role);

        return user;
    }

    public String generateJwtToken(Usuario user) {
        Instant expiresAt = LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.ofHours(-3));

        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("nome", user.getNome())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
    }

}

