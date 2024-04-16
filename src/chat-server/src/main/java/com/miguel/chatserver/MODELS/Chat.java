package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "chat_id")
  private Integer chatId;

  @ManyToMany
  @JoinTable(
    name = "chats_users",
    joinColumns = @JoinColumn(name = "chat_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private List<User> members;

  @OneToMany
  private List<Message> messages;

  public Chat() {
    this.members = new ArrayList<User>();
    this.messages = new ArrayList<Message>();
  }
}
