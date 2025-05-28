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

    public Usuario buscarPorId() {
        Long idLogado = getIdUsuarioLogado();
        return usuarioRepository.findById(idLogado)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
    }

    public Usuario atualizar(UsuarioDto dto) {
        Long idLogado = getIdUsuarioLogado();
        Usuario usuario = buscarPorId();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        return usuarioRepository.save(usuario);
    }

    public void deletar() {
        Long idLogado = getIdUsuarioLogado();
        Usuario usuario = buscarPorId();
        usuarioRepository.delete(usuario);
    }

    private Long getIdUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Usuario usuario) {
            String email = usuario.getEmail();
            if (email == null || email.isBlank()) {
                throw new UsernameNotFoundException("Email do usuário autenticado está vazio ou nulo");
            }
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email))
                    .getId();
        }

        throw new UsernameNotFoundException("Usuário autenticado não é uma instância de Usuario");
    }


}
