package by.epam.webtask.model.dao.impl;

import by.epam.webtask.exception.DaoException;
import by.epam.webtask.model.dao.BaseDao;
import by.epam.webtask.model.dao.UserDao;
import by.epam.webtask.model.entity.User;
import by.epam.webtask.model.mapper.impl.UserMapper;
import by.epam.webtask.model.pool.CustomConnectionPool;
import by.epam.webtask.model.pool.ProxyConnection;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.webtask.model.mapper.impl.UserMapper.PASSWORD;
import static by.epam.webtask.model.mapper.impl.UserMapper.USER_STATE;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_USERS = """
            SELECT usersId, name, surname, login, password, phoneNumber,
                                         status, role FROM fitnesscentre.users""";

    private static final String SQL_INSERT_NEW_USER = """ 
            INSERT INTO users(first_name, last_name, login, user_password, email, phone,
            state_id, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

    private static final String SQL_SELECT_USER_BY_ID = """
            SELECT user_id, first_name, last_name, login, user_password, email, phone, state, role FROM users
            JOIN user_state ON user_state.state_id = users.state_id
            JOIN user_role ON user_role.role_id = users.role_id
            WHERE user_id = (?)""";

    private static final String SQL_DELETE_USER_BY_ID = """
            DELETE FROM users WHERE user_id = (?)""";

    private static final String SQL_UPDATE_USER = """
            UPDATE users SET first_name = (?), last_name = (?), login = (?), user_password = (?), email = (?), 
            phone = (?), birthday = (?), discount_id = (?), state_id = (?), role_id = (?) 
            WHERE user_id = (?)""";

    public static final String SQL_SELECT_PASSWORD_BY_LOGIN = """
            SELECT user_password FROM users WHERE login = (?) """;

    private static final String SQL_SELECT_USER_BY_LOGIN = """
            SELECT users.user_id, first_name, last_name, login, user_password, email, phone,
            state, role_name FROM users
            JOIN user_state ON user_state.state_id = users.state_id
            JOIN user_role ON user_role.role_id = users.role_id WHERE login = (?)""";

    private static final String SQL_SELECT_USER_BY_PHONE_NUMBER = """
            SELECT users.user_id, first_name, last_name, login, user_password, email, phone, 
            state, role_name FROM users
            JOIN user_state ON user_state.state_id = users.state_id
            JOIN user_role ON user_role.role_id = users.role_id WHERE phone = (?)""";

    private static final String SQL_SELECT_USER_BY_EMAIL = """
            SELECT users.user_id, first_name, last_name, login, user_password, email, phone,
            state, role_name FROM users
            JOIN user_state ON user_state.state_id = users.state_id
            JOIN user_role ON user_role.role_id = users.role_id WHERE email = (?)""";

    private static final String SQL_SELECT_USER_STATE_BY_ID = """
            SELECT state FROM users JOIN user_state ON users.state_id = user_state.state_id
            WHERE users.user_id = (?)""";

    private static final String SQL_UPDATE_PASSWORD_BY_LOGIN = """
            UPDATE users SET user_password = (?) WHERE login = (?)""";

    private static final String SQL_UPDATE_USER_STATE_BY_LOGIN = """
            UPDATE users SET state_id = (?) where login = (?)""";

    private static final String SQL_UPDATE_USER_STATE_BY_ID = """
            UPDATE users SET state_id = (?) WHERE user_id = (?)""";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> userList = new ArrayList<>();
        try(ProxyConnection connection = (ProxyConnection) CustomConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);){
            while (resultSet.next()){
                Optional<User> optionalUser = new UserMapper().mapRow(resultSet);
                if(optionalUser.isPresent()) {
                    userList.add(optionalUser.get());
                }
            }
            logger.log(Level.INFO,"List: " + userList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public Optional<User> findEntityById(long id) throws DaoException {
        PreparedStatement statement = null;
        User user = null;
        try{
            statement = this.proxyConnection.prepareStatement(SQL_SELECT_USER_BY_ID);
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Optional<User> optionalUser = new UserMapper().mapRow(resultSet);
                if(optionalUser.isPresent()) {
                    user = optionalUser.get();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(statement);
        }
        return Optional.ofNullable(user); //TODO why
    }

    @Override
    public boolean delete(long id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = this.proxyConnection.prepareStatement(SQL_DELETE_USER_BY_ID);
            statement.setLong(1,id);
            return statement.executeUpdate() != 0 ? true : false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(statement);
        }
    }

    @Override
    public boolean delete(User entity) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = this.proxyConnection.prepareStatement(SQL_DELETE_USER_BY_ID);
            statement.setLong(1,entity.getUserId());
            return statement.executeUpdate() != 0 ? true : false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(statement);
        }
    }

    @Override
    public boolean create(User entity) throws DaoException {
        PreparedStatement statement = null;
        try{
            statement = this.proxyConnection.prepareStatement(SQL_INSERT_NEW_USER);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getLogin());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getEmail());
            statement.setInt(6, entity.getPhoneNumber());
            statement.setLong(7, entity.getState().getStateId());
            statement.setLong(8, entity.getRole().getRoleId());
            logger.log(Level.INFO,"The new row: " + entity);
            return statement.executeUpdate() != 0 ? true : false;
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(statement);
        }
    }

    @Override
    public boolean update(User entity) throws DaoException {
        PreparedStatement statement = null;
        try{
            statement = this.proxyConnection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getLogin());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getEmail());
            statement.setInt(6, entity.getPhoneNumber());
            statement.setLong(7, entity.getState().getStateId());
            statement.setLong(8, entity.getRole().getRoleId());
            statement.setLong(9,entity.getUserId());
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public Optional<String> findPasswordByLogin(String login) throws DaoException {
        PreparedStatement statement = null;
        Optional<String> password = Optional.empty();
        try{
            statement = this.proxyConnection.prepareStatement(SQL_SELECT_PASSWORD_BY_LOGIN);
            statement.setString(1,login);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                password = Optional.of(resultSet.getString(PASSWORD));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(statement);
        }
        return password;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        PreparedStatement statement = null;
        Optional<User> user = Optional.empty();
        try{
            statement = this.proxyConnection.prepareStatement(SQL_SELECT_USER_BY_LOGIN);
            statement.setString(1,login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                user = new UserMapper().mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(statement);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByPhoneNumber(int phone) throws DaoException {
        PreparedStatement statement = null;
        Optional<User> user = Optional.empty();
        try{
            statement = this.proxyConnection.prepareStatement(SQL_SELECT_USER_BY_PHONE_NUMBER);
            statement.setInt(1,phone);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                user = new UserMapper().mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(statement);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        PreparedStatement statement = null;
        Optional<User> user = Optional.empty();
        try{
            statement = this.proxyConnection.prepareStatement(SQL_SELECT_USER_BY_EMAIL);
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                user = new UserMapper().mapRow(resultSet);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }finally {
            close(statement);
        }
        return user;
    }

    @Override
    public Optional<User.UserState> findStateById(long userId) throws DaoException {
        PreparedStatement statement = null;
        Optional<User.UserState> userState = Optional.empty();
        try{
            statement = this.proxyConnection.prepareStatement(SQL_SELECT_USER_STATE_BY_ID);
            statement.setLong(1,userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                userState = Optional.of(User.UserState.valueOf(resultSet.getString(USER_STATE)
                        .trim().toUpperCase()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return userState;
    }

    @Override
    public boolean updatePasswordByLogin(String password, String login) throws DaoException {
        PreparedStatement statement = null;
        try{
            statement = this.proxyConnection.prepareStatement(SQL_UPDATE_PASSWORD_BY_LOGIN);
            statement.setString(1,password);
            statement.setString(2,login);
            return statement.executeUpdate() == 1 ? true : false;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean updateUserStateByLogin(User.UserState state, String login) throws DaoException{
        PreparedStatement statement = null;
        try{
            statement = this.proxyConnection.prepareStatement(SQL_UPDATE_USER_STATE_BY_LOGIN);
            statement.setLong(1,state.getStateId());
            statement.setString(2,login);
            return statement.executeUpdate() == 1 ? true : false;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean updateUserState(long userId, long stateId) throws DaoException {
        PreparedStatement statement = null;
        try{
            statement = this.proxyConnection.prepareStatement(SQL_UPDATE_USER_STATE_BY_ID);
            statement.setLong(1,stateId);
            statement.setLong(2,userId);
            return statement.executeUpdate() == 1 ? true : false;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }
}
