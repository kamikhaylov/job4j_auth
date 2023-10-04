package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.model.Person;
import ru.job4j.repository.api.PersonRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static ru.job4j.logging.PersonLogEvent.P0001;
import static ru.job4j.logging.PersonLogEvent.P0002;
import static ru.job4j.logging.PersonLogEvent.P0003;

/**
 * Сервис пользоватилей
 */
@Service
@AllArgsConstructor
public class PersonService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class.getName());

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Найти всех пользователей.
     *
     * @return список пользователей
     */
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    /**
     * Найти пользователя по id.
     *
     * @param id идентификатор пользователя
     * @return пользователь
     */
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    /**
     * Создать пользователя.
     *
     * @param person пользователь
     * @return пользователь
     */
    public Optional<Person> create(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Optional<Person> result = Optional.empty();
        try {
            result = Optional.of(personRepository.save(person));
        } catch (Exception ex) {
            LOGGER.error(P0001.toString(), ex);
        }
        return result;
    }

    /**
     * Обновить пользователя.
     *
     * @param person пользователь
     * @return результат обновления, true - обновлен, false - нет
     */
    public boolean update(Person person) {
        boolean result = false;
        Optional<Person> personFound = personRepository.findById(person.getId());

        if (personFound.isPresent()) {
            try {
                personRepository.save(person);
                result = true;
            } catch (Exception ex) {
                LOGGER.error(P0002.toString(), ex);
            }
        }

        return result;
    }

    /**
     * Удалить пользователя по id.
     *
     * @param id идентификатор пользователя
     * @return результат удаления, true - удален, false - нет
     */
    public boolean delete(int id) {
        Person person = new Person();
        person.setId(id);
        boolean result = false;
        Optional<Person> personFound = personRepository.findById(person.getId());

        if (personFound.isPresent()) {
            try {
                personRepository.delete(person);
                result = true;
            } catch (Exception ex) {
                LOGGER.error(P0003.toString(), ex);
            }
        }

        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Person user = personRepository.findByLogin(login);
        if (isNull(user)) {
            throw new UsernameNotFoundException(login);
        }
        return new User(user.getLogin(), user.getPassword(), emptyList());
    }
}
