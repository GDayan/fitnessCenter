package by.epam.webtask.model.dao;

import by.epam.webtask.exception.DaoException;
import by.epam.webtask.model.entity.Order;
import by.epam.webtask.model.entity.User;

import java.util.List;

public interface OrderDao {
    List<Order> findAllUserOrders(User user) throws DaoException;
}