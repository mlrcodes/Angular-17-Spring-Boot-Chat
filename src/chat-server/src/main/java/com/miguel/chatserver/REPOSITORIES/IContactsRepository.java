package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IContactsRepository extends JpaRepository<Contact, Integer> {

  public List<Contact> findByOwner(User owner);

  public Optional<Contact> findByOwnerAndContactUser(User owner, User contactUser);

  public Optional<Contact> findByContactUser(User contactUser);

  public Boolean existsByOwnerAndContactUser(User owner, User contactUser);

}
