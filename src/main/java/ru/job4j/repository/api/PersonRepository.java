package ru.job4j.repository.api;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.model.Person;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для взаимодействия с таблицей person
 */
@Primary
@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    /**
     * Найти всех пользователей.
     *
     * @return список пользователей
     */
    List<Person> findAll();

    Optional<Person> findByLogin(String login);
}
