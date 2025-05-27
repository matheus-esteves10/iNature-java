package br.com.fiap.iNature.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_ntr_localizacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Localizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_local")
    private Long id;

    @NotBlank(message = "A cidade é obrigatória.")
    @Size(max = 100, message = "O nome da cidade deve ter no máximo 100 caracteres.")
    @Column(name = "nm_cidade", nullable = false)
    private String cidade;

    @NotBlank(message = "O bairro é obrigatório.")
    @Size(max = 100, message = "O nome do bairro deve ter no máximo 100 caracteres.")
    @Column(name = "nm_bairro", nullable = false)
    private String bairro;

    @NotBlank(message = "O logradouro é obrigatório.")
    @Size(max = 120, message = "O logradouro deve ter no máximo 120 caracteres.")
    @Column(name = "nm_logradouro", nullable = false)
    private String logradouro;

    @Size(max = 7, message = "O número deve ter no máximo 7 caracteres.")
    @Column(name = "nr_numero")
    private String numero;
}