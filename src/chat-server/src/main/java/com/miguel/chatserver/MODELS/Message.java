package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Messages")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "message_id")
  private Integer messageId;

  @Column(nullable = false)
  private String messageText;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User sender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "chat_id", nullable = false)
  private Chat chat;

  @Column(nullable = false)
  private Date timeStamp;

}

