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
@Table(name = "t_ntr_reports")
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

    @NotBlank(message = "O tipo do report é obrigatório.")
    @Size(max = 50, message = "O tipo do report deve ter no máximo 50 caracteres.")
    @Column(name = "ds_tipo_report", nullable = false)
    private String tipo;

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

    public Report() {
    }

    public Report(Long id, String titulo, String corpo, String tipo, LocalDate data, Localizacao localizacao, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.corpo = corpo;
        this.tipo = tipo;
        this.data = data;
        this.localizacao = localizacao;
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

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
