package ru.job4j.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "Не заполнен логин")
    private String login;

    /** Пароль */
    @NotBlank(message = "Не заполнен пароль")
    private String password;
}