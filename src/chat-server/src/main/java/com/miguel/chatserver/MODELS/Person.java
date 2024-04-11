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

  @Column(nullable = false)
  private String firstname;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false, unique = true)
  private String phoneNumber;

  public Person(String firstname, String surname, String phoneNumber) {
    this.firstname = firstname;
    this.surname = surname;
    this.phoneNumber = phoneNumber;
  }
}
