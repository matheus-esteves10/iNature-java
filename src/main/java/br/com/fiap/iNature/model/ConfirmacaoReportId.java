package br.com.fiap.iNature.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmacaoReportId implements Serializable {

    @Column(name = "cd_id_report")
    private Long reportId;

    @Column(name = "cd_id_usuario")
    private Long usuarioId;
}
