package br.com.fiap.iNature.service;

import br.com.fiap.iNature.dto.Token;
import br.com.fiap.iNature.exceptions.UsuarioNotFoundException;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.model.enums.Role;
import br.com.fiap.iNature.repository.UserRepository;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private UserRepository usuarioRepository;

    public String createToken(Usuario user) {
        Instant expiresAt = LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.ofHours(-3));

        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("nome", user.getNome())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(Date.from(expiresAt))
                .sign(algorithm);
    }


    public Usuario getUserFromToken(String jwt) {
        var jwtVerified = JWT.require(algorithm).build().verify(jwt);
        Long userId = Long.valueOf(jwtVerified.getSubject());

        return usuarioRepository.findById(userId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado no banco"));
    }


    public Usuario getUsuarioLogado(String token) {
        return getUserFromToken(token.replace("Bearer ", ""));
    }


}

