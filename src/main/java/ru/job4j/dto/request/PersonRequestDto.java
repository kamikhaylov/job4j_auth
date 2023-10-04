package ru.job4j.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ДТО запроса пользователь
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonRequestDto {

    /** Идентификатор пользователя */
    private int id;

    /** Логин */
    private String login;

    /** Пароль */
    private String password;
}