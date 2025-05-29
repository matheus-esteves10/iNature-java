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
import java.util.ArrayList;
import java.util.List;

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

    @NotBlank
    @Size(max = 70)
    @Column(name = "nm_titulo_report", nullable = false)
    private String titulo;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "ds_corpo", nullable = false)
    private String corpo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "ds_tipo_report", nullable = false)
    private TipoReport tipo;

    @NotNull
    @Column(name = "dt_data_report", nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "cd_id_local", nullable = false)
    private Localizacao localizacao;

    @ManyToOne
    @JoinColumn(name = "cd_id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConfirmacaoReport> confirmacoes = new ArrayList<>();
}

