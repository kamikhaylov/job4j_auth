package ru.job4j.logging;

/**
 * Информация о логируемом событие.
 */
public enum PersonLogEvent implements LogEvent {

    P0001("Ошибка создания пользователя"),
    P0002("Ошибка обновления пользователя"),
    P0003("Ошибка удалениия пользователя");

    private final String title;

    PersonLogEvent(String title) {
        this.title = title;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return getCode() + ". " + getTitle();
    }
}
