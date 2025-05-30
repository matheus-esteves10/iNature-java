package br.com.fiap.iNature.service;

import br.com.fiap.iNature.dto.NoticiaDto;
import br.com.fiap.iNature.dto.response.NoticiaIdResponse;
import br.com.fiap.iNature.dto.response.NoticiaResponseDto;
import br.com.fiap.iNature.exceptions.NoticiaNotFoundException;
import br.com.fiap.iNature.exceptions.RoleNotPermitedException;
import br.com.fiap.iNature.model.Noticia;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.model.enums.Role;
import br.com.fiap.iNature.repository.NoticiaRepository;
import br.com.fiap.iNature.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;
    @Autowired
    private SupabaseStorageService supabaseStorageService;
    @Autowired
    private UserRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    @Transactional
    public NoticiaResponseDto criarNoticia(String token, NoticiaDto dto) throws IOException {
        Usuario autor = tokenService.getUsuarioLogado(token);

        if (autor.getRole() != Role.JORNALISTA) {
            throw new RoleNotPermitedException();
        }

        String nomeImagem = UUID.randomUUID() + "_" + dto.imagem().getOriginalFilename();
        String urlImagem = supabaseStorageService.uploadFile(dto.imagem(), nomeImagem).block();

        Noticia noticia = new Noticia();
        noticia.setTitulo(dto.titulo());
        noticia.setDataPublicacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        noticia.setResumo(dto.resumo());
        noticia.setCorpo(dto.corpo());
        noticia.setImagemCapa(urlImagem);
        noticia.setUsuario(autor);

        noticiaRepository.save(noticia);

        return new NoticiaResponseDto(
                noticia.getId(),
                noticia.getTitulo(),
                noticia.getDataPublicacao(),
                noticia.getResumo(),
                noticia.getImagemCapa(),
                noticia.getUsuario().getNome()
        );
    }

    public Page<NoticiaResponseDto> listarNoticias(Pageable pageable) {
        return noticiaRepository.findAll(pageable)
                .map(noticia -> new NoticiaResponseDto(
                        noticia.getId(),
                        noticia.getTitulo(),
                        noticia.getDataPublicacao(),
                        noticia.getResumo(),
                        noticia.getImagemCapa(),
                        noticia.getUsuario().getNome()
                ));
    }

    public NoticiaIdResponse buscarPorId(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(NoticiaNotFoundException::new);

        return new NoticiaIdResponse(
                noticia.getId(),
                noticia.getTitulo(),
                noticia.getDataPublicacao(),
                noticia.getResumo(),
                noticia.getCorpo(),
                noticia.getImagemCapa(),
                noticia.getUsuario().getNome()
        );
    }

}
