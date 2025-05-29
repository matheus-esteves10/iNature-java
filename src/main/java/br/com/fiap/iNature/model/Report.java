package br.com.fiap.iNature.model;

import br.com.fiap.iNature.model.enums.TipoReport;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@Table(name = "t_ntr_reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_report")
    private Long id;

    @NotBlank(message = "O título do report é obrigatório.")
    @Size(max = 70, message = "O título do report deve ter no máximo 70 caracteres.")
    @Column(name = "nm_titulo_report", nullable = false)
    private String titulo;

    @NotBlank(message = "O corpo do report é obrigatório.")
    @Size(max = 1000, message = "O corpo do report deve ter no máximo 1000 caracteres.")
    @Column(name = "ds_corpo", nullable = false)
    private String corpo;

    @NotNull(message = "O tipo do report é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "ds_tipo_report", nullable = false)
    private TipoReport tipo;

    @NotNull(message = "A data do report é obrigatória.")
    @Column(name = "dt_data_report", nullable = false)
    private LocalDate data;

    @NotNull(message = "A localização do report é obrigatória.")
    @ManyToOne
    @JoinColumn(name = "cd_id_local", nullable = false)
    private Localizacao localizacao;

    @NotNull(message = "O usuário responsável pelo report é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "cd_id_usuario", nullable = false)
    private Usuario usuario;

}
