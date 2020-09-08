package com.novoseltsev.dictionaryapi.repository;

import com.novoseltsev.dictionaryapi.domain.entity.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String login);
}
