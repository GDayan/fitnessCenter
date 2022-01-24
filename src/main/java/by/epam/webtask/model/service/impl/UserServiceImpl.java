package by.epam.webtask.model.service.impl;

import by.epam.webtask.exception.DaoException;
import by.epam.webtask.model.dao.EntityTransaction;
import by.epam.webtask.model.dao.impl.UserDaoImpl;
import by.epam.webtask.model.entity.User;
import by.epam.webtask.model.service.UserService;
import by.epam.webtask.util.PasswordEncryption;
import by.epam.webtask.util.TextUtil;
import com.google.protobuf.ServiceException;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        UserDaoImpl userDao = new UserDaoImpl();
        login = TextUtil.escapeText(login);
        password = TextUtil.escapeText(password);
        EntityTransaction transaction = new EntityTransaction();
        transaction.init(userDao);
        String encryptPassword = PasswordEncryption.md5Apache(password);
        try {
            return userDao.findUserByLoginAndPassword(login, encryptPassword);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }
}
