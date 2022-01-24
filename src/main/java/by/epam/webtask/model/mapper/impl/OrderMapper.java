package by.epam.webtask.model.mapper.impl;

import by.epam.webtask.exception.DaoException;
import by.epam.webtask.model.entity.Order;
import by.epam.webtask.model.mapper.CustomRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static by.epam.webtask.model.mapper.impl.UserMapper.USER_ID;

public class OrderMapper implements CustomRowMapper<Order> {
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_DATE = "order_date";
    public static final String EXERCISES = "exercises";
    public static final String NUTRITION = "nutrition";
    public static final String PRICE = "price";
    public static final String IS_ACTIVE = "isActive";
    @Override
    public Optional<Order> mapRow(ResultSet resultSet) throws DaoException {
        Order order = new Order();
        Optional<Order> optionalOrder;//TODO
        try{
            order.setOrderId(resultSet.getLong(ORDER_ID));
            order.setOrderDate(resultSet.getTimestamp(ORDER_DATE).toLocalDateTime());
            order.setExercises(resultSet.getString(EXERCISES));
            order.setNutrition(resultSet.getString(NUTRITION));
            order.setPrice(resultSet.getInt(PRICE));
            order.setIsActive(resultSet.getBoolean(IS_ACTIVE));
            order.setUserId(resultSet.getLong(USER_ID));
            optionalOrder = Optional.of(order);
        } catch (SQLException e) {
            //TODO log
            optionalOrder = Optional.empty();
        }
        return optionalOrder;
    }
}
