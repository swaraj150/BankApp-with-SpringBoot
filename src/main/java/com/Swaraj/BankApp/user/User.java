package com.Swaraj.BankApp.user;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

@Table(name = "bank_user")

public class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "seq_gen",initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_gen")
    @Column(unique = true)
    private Integer account;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String pin;
    @Column(nullable = false)
    private double balance;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));// role.name() returns name of enum constant role.
    }

    @Override
    public String getPassword() {
        return this.pin;
    }

    @Override
    public String getUsername() {
        return Integer.toString(this.account);
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
