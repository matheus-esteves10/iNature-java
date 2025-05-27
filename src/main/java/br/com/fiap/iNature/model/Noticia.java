package br.com.fiap.iNature.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_ntr_noticia")
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_noticia")
    private Long id;

    @NotBlank(message = "O título da notícia é obrigatório.")
    @Size(max = 255, message = "O título da notícia deve ter no máximo 255 caracteres.")
    @Column(name = "nm_titulo", nullable = false)
    private String titulo;

    @NotNull(message = "A data de publicação é obrigatória.")
    @Column(name = "dt_data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Size(max = 500, message = "O resumo deve ter no máximo 500 caracteres.")
    @Column(name = "ds_resumo")
    private String resumo;

    @NotBlank(message = "O corpo da notícia é obrigatório.")
    @Size(max = 2000, message = "O corpo da notícia deve ter no máximo 2000 caracteres.")
    @Column(name = "ds_corpo", nullable = false)
    private String corpo;

    @Size(max = 255, message = "A URL da imagem de capa deve ter no máximo 255 caracteres.")
    @Column(name = "img_capa")
    private String imagemCapa;

    @NotNull(message = "O autor da notícia é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "cd_id_usuario", nullable = false)
    private Usuario usuario;


    public Noticia() {
    }

    public Noticia(Long id, String titulo, LocalDate dataPublicacao, String resumo, String corpo, String imagemCapa, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.dataPublicacao = dataPublicacao;
        this.resumo = resumo;
        this.corpo = corpo;
        this.imagemCapa = imagemCapa;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(LocalDate dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getImagemCapa() {
        return imagemCapa;
    }

    public void setImagemCapa(String imagemCapa) {
        this.imagemCapa = imagemCapa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
