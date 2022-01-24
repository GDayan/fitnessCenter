package by.epam.webtask.model.dao;

import by.epam.webtask.exception.DaoException;
import by.epam.webtask.model.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<String> findPasswordByLogin(String login) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    Optional<User> findUserByPhoneNumber(int phoneNumber) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    Optional<User.UserState> findStateById(long userId) throws DaoException;

    boolean updatePasswordByLogin(String password, String login) throws DaoException;

    boolean updateUserStateByLogin(User.UserState state, String login) throws DaoException;

    boolean updateUserState(long userId, long stateId) throws DaoException;

    Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException;
}
