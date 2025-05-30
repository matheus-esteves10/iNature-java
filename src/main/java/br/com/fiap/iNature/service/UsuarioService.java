package br.com.fiap.iNature.service;

import br.com.fiap.iNature.dto.UsuarioDto;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.model.enums.Role;
import br.com.fiap.iNature.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Transactional
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
        return tokenService.getUsuarioLogado();
    }


    @Transactional
    public Usuario atualizar(UsuarioDto dto) {

        Usuario usuario = tokenService.getUsuarioLogado();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        return usuarioRepository.save(usuario);
    }


    @Transactional
    public void deletar() {

        Usuario usuario = tokenService.getUsuarioLogado();

        usuarioRepository.deleteById(usuario.getId());
    }

}
