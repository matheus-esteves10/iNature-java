package br.com.fiap.iNature.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(name = "t_ntr_report_confirmacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmacaoReport implements Serializable {

    @EmbeddedId
    private ConfirmacaoReportId id;

    @ManyToOne
    @MapsId("reportId")
    @JoinColumn(name = "cd_id_report")
    private Report report;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "cd_id_usuario")
    private Usuario usuario;

    @Column(name = "dt_confirmacao")
    private LocalDate dataConfirmacao;
}
