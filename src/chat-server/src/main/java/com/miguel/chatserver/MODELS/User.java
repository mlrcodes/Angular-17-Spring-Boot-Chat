package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User implements UserDetails {

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

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private ArrayList<Contact> contacts;

  public User(String firstname, String surname, String phoneNumber) {
    this.firstname = firstname;
    this.surname = surname;
    this.phoneNumber = phoneNumber;
    this.contacts = new ArrayList<Contact>();
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
