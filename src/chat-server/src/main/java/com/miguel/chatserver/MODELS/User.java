package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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

  @NotBlank
  @Column(nullable = false)
  private String password;

  private Contact[] contacts;

}
