package ru.job4j.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.job4j.dto.response.PersonResponseDto;
import ru.job4j.model.Person;

/**
 * Маппер модель хранения пользователя в ДТО ответа пользователя
 */
@Component
public class PersonResponseMapper implements Mapper<Person, PersonResponseDto> {

    private final ModelMapper mapper;

    public PersonResponseMapper() {
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public PersonResponseDto map(Person person) {
        return this.mapper.map(person, PersonResponseDto.class);
    }
}
