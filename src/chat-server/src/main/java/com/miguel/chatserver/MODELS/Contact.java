package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Contacts")
public class Contact extends Person{
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer contactId;

}
