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
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
