package com.aftasapi.entity;

import com.aftasapi.entity.enums.IdentityDocumentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String familyName;
    private LocalDate accessionDate;
    private String nationality;
    private Boolean status ;
    @Enumerated(EnumType.STRING)
    private IdentityDocumentType identityDocumentType;
    @Column(unique = true)
    private String identityNumber;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @JsonIgnore
    @NotNull
    @Size(min = 4, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ranking> rankings;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Hunting> huntings;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    private Collection<AppRole> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return status;
    }
}

