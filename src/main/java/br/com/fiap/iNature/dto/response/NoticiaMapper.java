package br.com.fiap.iNature.dto.response;

import br.com.fiap.iNature.model.Noticia;

public class NoticiaMapper {

    public static NoticiaResponseDto toResponseDto(Noticia noticia) {
        return new NoticiaResponseDto(
                noticia.getId(),
                noticia.getTitulo(),
                noticia.getDataPublicacao(),
                noticia.getResumo(),
                noticia.getImagemCapa(),
                noticia.getUsuario().getNome()
        );
    }

    public static NoticiaSelecionadaResponse toSelecionadaResponse(Noticia noticia) {
        return new NoticiaSelecionadaResponse(
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

