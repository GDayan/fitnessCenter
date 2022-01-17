package by.epam.webtask.model.mapper;

import by.epam.webtask.model.entity.CustomEntity;
import by.epam.webtask.exception.DaoException;

import java.sql.ResultSet;
import java.util.Optional;

@FunctionalInterface
public interface CustomRowMapper<T extends CustomEntity> {
    Optional<T> mapRow(ResultSet resultSet) throws DaoException;
}