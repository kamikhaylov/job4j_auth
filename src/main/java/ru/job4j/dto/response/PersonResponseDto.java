package ru.job4j.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * ДТО ответа пользователь
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonResponseDto {

    /** Идентификатор пользователя */
    @NotNull(message = "Не заполнен идентификатор")
    private int id;

    /** Логин */
    @NotBlank(message = "Не заполнен логин")
    private String login;

    /** Пароль */
    @NotBlank(message = "Не заполнен пароль")
    private String password;
}