package expert.productional;

import expert.productional.except.ExistsException;

/**
 * Интерфейс описывающий факт.
 * Факт состояит из уникального названия и подробного текстового описания.
 * Названия задаются пользователем, но они должны быть уникальны.
 */
public interface Term {
    /**
     * Установка название, которое должно быть уникальным.
     * @param name название факта.
     * @throws ExistsException если факт с таким названием уже существует.
     */
    void setName(String name) throws ExistsException;

    /**
     *
     * @return Название факта.
     */
    String getName();

    /**
     * Установка подробного текстового описания факта.
     * @param description текстовое описание факта.
     */
    void setDescription(String description);

    /**
     *
     * @return Текстовое описание факта.
     */
    String getDescription();
}
