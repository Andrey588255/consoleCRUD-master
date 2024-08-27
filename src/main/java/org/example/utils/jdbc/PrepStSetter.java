package org.example.utils.jdbc;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Functional interface for setting up SQL {@link java.sql.PreparedStatement} parameters.
 *
 * Для всех сущностей сохранение в базу происходит одинаково, единственное отличие - установка параметров PrepareStatement,
 * этот функциональный интерфейс служит для установки этих параметров, таким образом я во всех репозиториях использую один и
 * тот же метод DBWorker-а, но лишь каждый раз устанавливаю разные параметры в запрос. Без этого интерфейса придется
 * дублировать один и тот же код во всех репозиториях
 */
@FunctionalInterface
public interface PrepStSetter {
    void setUp(PreparedStatement prepSt) throws SQLException;

}
