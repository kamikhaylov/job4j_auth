package ru.job4j.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.model.Person;
import ru.job4j.service.PersonService;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер пользователей
 */
@RestController
@AllArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    /** Получить список пользователей */
    @GetMapping("/list")
    public List<Person> findAll() {
        return this.personService.findAll();
    }

    /** Получить пользователя по id */
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> person = personService.findById(id);
        return new ResponseEntity<>(
                person.orElse(null),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    /** Создать пользователя */
    @PostMapping("/create")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Optional<Person> result = personService.create(person);
        return new ResponseEntity<>(
                result.orElse(person),
                result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    /** Обновить пользователя */
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        return personService.update(person) ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }

    /** Удалить пользователя по id */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return personService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
    }
}
