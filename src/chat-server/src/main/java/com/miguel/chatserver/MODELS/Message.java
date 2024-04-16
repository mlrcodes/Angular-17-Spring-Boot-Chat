package com.miguel.chatserver.MODELS;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User sender;

  @Column(nullable = false)
  private LocalDateTime dateTime;


}

