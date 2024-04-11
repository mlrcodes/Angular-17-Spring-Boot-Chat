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
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "contact_id")
  private Integer contactId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User owner;

  @OneToOne
  @JoinColumn(name = "contact_id", insertable = false, updatable = false)
  private User contactUser;

}
