package ru.job4j.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.job4j.dto.request.PersonRequestDto;
import ru.job4j.model.Person;

/**
 * Маппер ДТО запроса пользователя в модель хранения пользователя
 */
@Component
public class PersonRequestMapper implements Mapper<PersonRequestDto, Person> {

    private final ModelMapper mapper;

    public PersonRequestMapper() {
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public Person map(PersonRequestDto personRequestDto) {
        return this.mapper.map(personRequestDto, Person.class);
    }
}
