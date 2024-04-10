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
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Persons")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer personId;

  @NotBlank
  @Column(nullable = false)
  private String firstname;

  @NotBlank
  @Column(nullable = false)
  private String surname;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String phoneNumber;

}
