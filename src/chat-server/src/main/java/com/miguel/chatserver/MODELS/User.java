package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Users")
public class User implements UserDetails, Principal {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id")
  private Integer userId;

  @Column(nullable = false)
  private String firstname;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false, unique = true)
  private String phoneNumber;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Boolean acceptedTerms;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private List<Contact> contacts;

  private Boolean enabled;

  public User(String firstname, String surname, String phoneNumber, String email, Boolean acceptedTerms) {
    this.firstname = firstname;
    this.surname = surname;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.contacts = new ArrayList<Contact>();
    this.enabled = true;
    this.acceptedTerms = acceptedTerms;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return phoneNumber;
  }
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }


  private String getFullName() {
    return firstname + " " + surname;
  }

  @Override
  public String getName() {
    return phoneNumber;
  }

  @Override
  public boolean implies(Subject subject) {
    return Principal.super.implies(subject);
  }
}
