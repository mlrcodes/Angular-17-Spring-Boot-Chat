package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User extends Person {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer personId;

  @Column(nullable = false)
  private String password;

  private ArrayList<Contact> contacts;
  public User(String firstname, String surname, String phoneNumber) {
    super(firstname, surname, phoneNumber);
    this.contacts = new ArrayList<Contact>();
  }
}
