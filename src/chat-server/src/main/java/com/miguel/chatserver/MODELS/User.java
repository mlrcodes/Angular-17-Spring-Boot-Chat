package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Users")
public class User {

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

  @Column(nullable = false)
  private Boolean acceptedTerms;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  private List<Contact> contacts;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Chat> chats;
}
