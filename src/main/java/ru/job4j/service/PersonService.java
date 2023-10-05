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
import ru.job4j.dto.request.PersonRequestDto;
import ru.job4j.dto.response.PersonResponseDto;
import ru.job4j.mapper.Mapper;
import ru.job4j.model.Person;
import ru.job4j.repository.api.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
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
    private final Mapper<PersonRequestDto, Person> personRequestMapper;
    private final Mapper<Person, PersonResponseDto> personResponseMapper;

    /**
     * Найти всех пользователей.
     *
     * @return список пользователей
     */
    public List<PersonResponseDto> findAll() {
        List<Person> personList = personRepository.findAll();
        return personList.stream()
                       .map(personResponseMapper::map)
                       .collect(Collectors.toList());
    }

    /**
     * Найти пользователя по id.
     *
     * @param id идентификатор пользователя
     * @return пользователь
     */
    public Optional<PersonResponseDto> findById(int id) {
        Optional<Person> person = personRepository.findById(id);
        return person.map(personResponseMapper::map);
    }

    /**
     * Создать пользователя.
     *
     * @param personRequestDto пользователь
     * @return пользователь
     */
    public Optional<PersonResponseDto> create(PersonRequestDto personRequestDto) {
        Person person = personRequestMapper.map(personRequestDto);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Optional<PersonResponseDto> result = Optional.empty();
        try {
            result = Optional.of(personResponseMapper.map(personRepository.save(person)));
        } catch (Exception ex) {
            LOGGER.error(P0001.toString(), ex);
        }
        return result;
    }

    /**
     * Обновить пользователя.
     *
     * @param personRequestDto пользователь
     * @return результат обновления, true - обновлен, false - нет
     */
    public boolean update(PersonRequestDto personRequestDto) {
        boolean result = false;
        Person person = personRequestMapper.map(personRequestDto);
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
        Optional<Person> person = personRepository.findByLogin(login);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        return new User(person.get().getLogin(), person.get().getPassword(), emptyList());
    }
}
