package by.epam.webtask.model.service;

import by.epam.webtask.model.entity.User;
import com.google.protobuf.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> signIn(String login, String password) throws ServiceException;
}
