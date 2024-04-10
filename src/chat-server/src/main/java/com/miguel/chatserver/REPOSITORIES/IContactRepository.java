package com.miguel.chatserver.REPOSITORIES;

import com.miguel.chatserver.MODELS.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Integer> {
}
