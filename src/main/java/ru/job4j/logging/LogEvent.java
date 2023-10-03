package ru.job4j.logging;

/**
 * Информация о логируемом событие.
 */
public interface LogEvent {

    /**
     * Код события.
     * @return код события
     */
    String getCode();

    /**
     * Заголовок.
     * @return заголовок события
     */
    String getTitle();
}
