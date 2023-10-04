package ru.job4j.mapper;

/**
 * Маппер модели
 */
public interface Mapper<P, T> {

    /**
     * Маппер объекта типа P в объект типа T
     * @param p - источник
     * @return полученный объект
     */
    T map(P p);
}
