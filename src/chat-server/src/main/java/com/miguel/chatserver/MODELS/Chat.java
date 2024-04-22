package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "chat_id")
  private Integer chatId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @OneToOne
  @JoinColumn(name = "contact_id", referencedColumnName = "contact_id")
  private Contact contact;

  @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
  private List<Message> messages;
}
