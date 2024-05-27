package com.desafio.java.pitang.model.entity;

import com.desafio.java.pitang.util.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    private Date birthday;
    @Column(name = "login", unique = true)
    private String login;
    private String password;
    private String phone;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private List<Carro> cars;
    private UserRole role;
    private Date lastLogin;
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.USER) return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of();
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
