package ru.job4j.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.job4j.dto.request.PersonRequestDto;

import static java.util.Objects.isNull;
import static ru.job4j.logging.PersonLogEvent.P0005;
import static ru.job4j.logging.PersonLogEvent.P0006;
import static ru.job4j.logging.PersonLogEvent.P0007;

/**
 * Валидация входных параметров контроллера пользователей
 */
@Component
public class PersonResponseValidator {

    public void validateAndThrow(PersonRequestDto person) {
        if (isNull(person)) {
            throw new NullPointerException(P0005.toString());
        }
        if  (StringUtils.isEmpty(person.getLogin())) {
            throw new NullPointerException(P0006.toString());
        }
        if  (StringUtils.isEmpty(person.getPassword())) {
            throw new NullPointerException(P0007.toString());
        }
    }
}
