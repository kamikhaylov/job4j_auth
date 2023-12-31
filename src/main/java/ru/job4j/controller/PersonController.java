package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.dto.request.PersonRequestDto;
import ru.job4j.dto.response.PersonResponseDto;
import ru.job4j.service.PersonService;
import ru.job4j.validation.PersonResponseValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static ru.job4j.logging.PersonLogEvent.P0004;
import static ru.job4j.logging.PersonLogEvent.P0008;
import static ru.job4j.logging.PersonLogEvent.P0009;

/**
 * Контроллер пользователей
 */
@RestController
@AllArgsConstructor
@RequestMapping("/person")
@ControllerAdvice
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getName());

    private final PersonService personService;
    private final PersonResponseValidator validator;
    private final ObjectMapper objectMapper;

    /** Получить список пользователей */
    @GetMapping("/list")
    public List<PersonResponseDto> findAll() {
        return this.personService.findAll();
    }

    /** Получить пользователя по id */
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable int id) {
        Optional<PersonResponseDto> person = personService.findById(id);
        return new ResponseEntity<>(
                person.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, P0004.toString())), HttpStatus.OK);
    }

    /** Создать пользователя */
    @PostMapping("/create")
    public ResponseEntity<PersonResponseDto> create(@Valid @RequestBody PersonRequestDto person) {
        validator.validateAndThrow(person);
        Optional<PersonResponseDto> result = personService.create(person);
        return new ResponseEntity<>(result.isPresent() ? HttpStatus.CREATED : HttpStatus.CONFLICT);
    }

    /** Обновить пользователя */
    @PatchMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody PersonRequestDto person) {
        validator.validateAndThrow(person);
        if (!personService.update(person)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, P0004.toString());
        }
        return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(P0008.toString());
    }

    /** Удалить пользователя по id */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (!personService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, P0004.toString());
        }
        return ResponseEntity
                       .status(HttpStatus.OK)
                       .body(P0009.toString());
    }

    /** Обработка исключения */
    @ExceptionHandler(value = {IllegalArgumentException.class, NullPointerException.class})
    public void exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", exception.getMessage());
                put("type", exception.getClass());
            }
        }));
        LOGGER.error(exception.getLocalizedMessage());
    }
}
