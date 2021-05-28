package com.example.service_ticket.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;

    private String email;

    private String firstname;
    private String lastname;
    private String category;
    private final boolean enabled;
    // Отражает разрешения(роли) выданые пользователю
    private final Collection<? extends GrantedAuthority> authorities;

    // Конструктор пользователя


    public JwtUser(Long id, String username, String email,String password, String firstname, String lastname, String category,  boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.enabled = enabled;
        this.authorities = authorities;
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public Long getId (){
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Указание срока истечения пользователя
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Указание заблокированого пользователя
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Указание срока истечения данных пользователя
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Проверка заблокированости пользователя
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
