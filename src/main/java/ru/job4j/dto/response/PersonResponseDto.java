package ru.job4j.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ДТО ответа пользователь
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonResponseDto {

    /** Идентификатор пользователя */
    private int id;

    /** Логин */
    private String login;

    /** Пароль */
    private String password;
}