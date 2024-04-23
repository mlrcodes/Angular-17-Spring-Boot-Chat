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
  @JoinColumn(name = "owner_user_id", nullable = false)
  private User owner;

  @ManyToOne
  @JoinColumn(name = "contact_user_id", nullable = false)
  private User contactUser;

  /*
  @OneToOne(mappedBy = "contact", cascade = CascadeType.ALL)
  private Chat chat;

   */
}
