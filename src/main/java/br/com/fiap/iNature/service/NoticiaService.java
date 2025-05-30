package br.com.fiap.iNature.service;

import br.com.fiap.iNature.dto.NoticiaDto;
import br.com.fiap.iNature.dto.response.NoticiaSelecionadaResponse;
import br.com.fiap.iNature.dto.response.NoticiaMapper;
import br.com.fiap.iNature.dto.response.NoticiaResponseDto;
import br.com.fiap.iNature.exceptions.NoticiaNotFoundException;
import br.com.fiap.iNature.exceptions.RoleNotPermitedException;
import br.com.fiap.iNature.model.Noticia;
import br.com.fiap.iNature.model.Usuario;
import br.com.fiap.iNature.model.enums.Role;
import br.com.fiap.iNature.repository.NoticiaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private TokenService tokenService;

    @Transactional
    public NoticiaResponseDto criarNoticia(NoticiaDto dto) throws IOException {
        Usuario autor = tokenService.getUsuarioLogado();

        if (autor.getRole() != Role.JORNALISTA) {
            throw new RoleNotPermitedException();
        }

        String urlImagem = salvarImagem(dto.imagem());

        Noticia noticia = new Noticia();
        noticia.setTitulo(dto.titulo());
        noticia.setDataPublicacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        noticia.setResumo(dto.resumo());
        noticia.setCorpo(dto.corpo());
        noticia.setImagemCapa(urlImagem);
        noticia.setUsuario(autor);

        noticiaRepository.save(noticia);

        return NoticiaMapper.toResponseDto(noticia);
    }

    public Page<NoticiaResponseDto> listarNoticias(Pageable pageable) {
        return noticiaRepository.findAll(pageable)
                .map(NoticiaMapper::toResponseDto);
    }

    public NoticiaSelecionadaResponse buscarPorId(Long id) {
        Noticia noticia = noticiaRepository.findById(id)
                .orElseThrow(NoticiaNotFoundException::new);

        return NoticiaMapper.toSelecionadaResponse(noticia);
    }

    // Auxiliares
    private String salvarImagem(MultipartFile imagem) throws IOException {
        String nomeImagem = UUID.randomUUID() + "_" + imagem.getOriginalFilename();
        return supabaseStorageService.uploadFile(imagem, nomeImagem).block();
    }

}
