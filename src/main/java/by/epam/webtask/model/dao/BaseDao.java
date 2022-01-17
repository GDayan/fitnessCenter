package by.epam.webtask.model.dao;

import by.epam.webtask.model.entity.CustomEntity;
import by.epam.webtask.exception.DaoException;
import by.epam.webtask.model.pool.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public abstract class BaseDao<T extends CustomEntity>{
    static final Logger logger = LogManager.getLogger();
    protected ProxyConnection proxyConnection;

    public abstract List<T> findAll() throws DaoException;
    public abstract Optional<T> findEntityById(long id) throws DaoException;
    public abstract boolean delete(T t) throws DaoException;
    public abstract boolean delete(long id) throws DaoException;
    public abstract boolean create(T t) throws DaoException;
    public abstract boolean update(T t) throws DaoException;

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {

        }
    }

    protected void setConnection(Connection connection){
        this.proxyConnection = (ProxyConnection) connection;
    }
}
