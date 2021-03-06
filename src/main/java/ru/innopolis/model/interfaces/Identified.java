package ru.innopolis.model.interfaces;

import java.io.Serializable;

/**
 * Интерфейс идентифицируемых объектов.
 */
public interface Identified<PK extends Serializable> extends Serializable {

    /** Возвращает идентификатор объекта */
    PK getId();

}
