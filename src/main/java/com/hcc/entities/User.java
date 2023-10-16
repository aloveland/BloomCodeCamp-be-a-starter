package com.hcc.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming you want to auto-generate the ID
    private Long id;

    @Column(name = "cohort_start_date")  // Assuming the column name is 'cohort_start_date'
    private Date cohortStartDate;

    @Column(name = "username")  // Assuming the column name is 'username'
    private String username;

    @Column(name = "password")  // Assuming the column name is 'password'
    private String password;

    // Assuming authorities is a list of strings representing authority names.
    // Adjust the generic type if it's supposed to be a list of some other type.

    public User(){

    }
    public User(Date cohortStartDate, String username, String password){
        this.cohortStartDate = cohortStartDate;
        this.username = username;
        this.password = password;
    }
    @ElementCollection
    @Column(name = "authorities")  // Assuming the column name is 'authorities'
    private List<String> authorities;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCohortStartDate() {
        return cohortStartDate;
    }

    public void setCohortStartDate(Date cohortStartDate) {
        this.cohortStartDate = cohortStartDate;
    }


    public void setUsername(String username) {
        this.username = username;
    }



    public void setPassword(String password) {
        this.password = password;
    }


    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new Authority("role_student"));
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
