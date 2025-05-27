package br.com.fiap.iNature.model;

import br.com.fiap.iNature.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "t_ntr_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_id_usuario")
    private Long id;

    @NotBlank(message = "O nome do usuário é obrigatório.")
    @Size(max = 120, message = "O nome do usuário deve ter no máximo 120 caracteres.")
    @Column(name = "nm_usuario", nullable = false)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Size(max = 255, message = "O e-mail deve ter no máximo 255 caracteres.")
    @Email(message = "O e-mail informado é inválido.")
    @Column(name = "nm_email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(max = 255, message = "A senha deve ter no máximo 255 caracteres.")
    @Column(name = "ds_senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "nm_role", nullable = false)
    private Role role;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String email, String senha, Role role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
