package by.epam.webtask.model.dao.impl;

import by.epam.webtask.exception.DaoException;
import by.epam.webtask.model.dao.BaseDao;
import by.epam.webtask.model.dao.OrderDao;
import by.epam.webtask.model.entity.Order;
import by.epam.webtask.model.entity.User;
import by.epam.webtask.model.mapper.impl.OrderMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDaoImpl extends BaseDao<Order> implements OrderDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_ORDERS = """
            SELECT orderId, orderDate, exercises, nutrition, price, isActive, userId FROM orders""";

    private static final String SQL_SELECT_ORDER_BY_ID = """
            SELECT orderId, orderDate, exercises, nutrition, price, isActive, userId 
            FROM orders WHERE orderId = (?)""";

    private static final String SQL_DELETE_ORDER = """
            DELETE FROM orders WHERE orderId = (?)""";

    private static final String SQL_INSERT_NEW_ORDER = """
            INSERT INTO orders(orderId, orderDate, exercises, nutrition, price, isActive, userId) VALUES (?, ?, ?, ?, ?, ?, ?)""";

    private static final String SQL_UPDATE_ORDER = """
            UPDATE orders SET orderId = (?), orderDate = (?), exercises = (?), 
            nutrition = (?), price = (?), isActive = (?),  userId  = (?)
            WHERE order_id = (?)""";

    private static final String SQL_SELECT_ALL_USER_ORDERS = """
            SELECT orderId, orderDate, exercises, nutrition, price, isActive, userId 
            FROM orders WHERE userId = (?)""";

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_ORDERS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Optional<Order> optionalOrder = new OrderMapper().mapRow(resultSet);
                if (optionalOrder.isPresent()) {
                    orderList.add(optionalOrder.get());
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return orderList;
    }

    @Override
    public Optional<Order> findEntityById(long id) throws DaoException {
        try (PreparedStatement statement = this.proxyConnection.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new OrderMapper().mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(long id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = this.proxyConnection.prepareStatement(SQL_DELETE_ORDER);
            statement.setLong(1, id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }

    }

    @Override
    public boolean delete(Order entity) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = this.proxyConnection.prepareStatement(SQL_DELETE_ORDER);
            statement.setLong(1, entity.getOrderId());//TODO why?
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean create(Order entity) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = this.proxyConnection.prepareStatement(SQL_INSERT_NEW_ORDER);
            statement.setLong(1, entity.getOrderId());
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(entity.getOrderDate()));
            statement.setString(3, entity.getExercises());
            statement.setString(4, entity.getNutrition());
            statement.setInt(5, entity.getPrice());
            statement.setBoolean(6, entity.getIsActive());
            statement.setLong(7, entity.getUserId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean update(Order entity) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = this.proxyConnection.prepareStatement(SQL_UPDATE_ORDER);
            statement.setLong(1, entity.getOrderId());
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(entity.getOrderDate()));
            statement.setString(3, entity.getExercises());
            statement.setString(4, entity.getNutrition());
            statement.setInt(5, entity.getPrice());
            statement.setBoolean(6, entity.getIsActive());
            statement.setLong(7, entity.getUserId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public List<Order> findAllUserOrders(User user) throws DaoException {
        List<Order> orderList = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = this.proxyConnection.prepareStatement(SQL_SELECT_ALL_USER_ORDERS);
            statement.setLong(1, user.getUserId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Optional<Order> optionalOrder = new OrderMapper().mapRow(resultSet);
                if (optionalOrder.isPresent()) {
                    orderList.add(optionalOrder.get());
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return orderList;
    }
}
