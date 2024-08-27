package org.example.utils.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface

public interface ResultSetParser<T>{
    T parse(ResultSet resultSet) throws SQLException;
}