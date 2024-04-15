package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Contacts")
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "contact_id")
  private Integer contactId;


  @Column(nullable = false)
  private String contactName;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User owner;

  @OneToOne
  @JoinColumn(name = "contact_id", insertable = false, updatable = false)
  private User contactUser;

}
