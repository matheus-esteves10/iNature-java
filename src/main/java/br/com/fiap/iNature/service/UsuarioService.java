package br.com.fiap.iNature.service;

import br.com.fiap.iNature.dto.UsuarioDto;
import br.com.fiap.iNature.exceptions.AcessoNegadoException;
import br.com.fiap.iNature.exceptions.UsuarioNotFoundException;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.model.enums.Role;
import br.com.fiap.iNature.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    // CREATE
    public Usuario salvar(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setRole(Role.USUARIO);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(String token) {
        Long id = getUsuarioLogado(token);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public Usuario atualizar(String token, UsuarioDto dto) {
        Long id = getUsuarioLogado(token);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        return usuarioRepository.save(usuario);
    }

    public void deletar(String token) {
        Long id = getUsuarioLogado(token);

        if (!usuarioRepository.existsById(id)) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);
    }

    private Long getUsuarioLogado(String token) {
        Usuario usuario = tokenService.getUserFromToken(token.replace("Bearer ", ""));
        return usuario.getId();
    }



}
