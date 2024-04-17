package com.miguel.chatserver.MODELS;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

/*
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

  @Id
  @Generated
  Integer tokenId;

  private String token;

  private Date createdAt;

  private Date expiresAt;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Token(String token, Date createdAt, Date expiresAt, User user) {
    this.token = token;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
    this.user = user;
  }
}

 */
