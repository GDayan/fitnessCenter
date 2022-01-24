package by.epam.webtask.controller.command.impl;

import by.epam.webtask.controller.Router;
import by.epam.webtask.controller.command.Command;
import by.epam.webtask.exception.CommandException;
import by.epam.webtask.model.entity.User;
import by.epam.webtask.model.service.UserService;
import by.epam.webtask.model.service.impl.UserServiceImpl;
import com.google.protobuf.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static by.epam.webtask.controller.Parameter.*;
import static by.epam.webtask.controller.PathPage.*;
import static by.epam.webtask.controller.PropertiesKey.ERROR_INCORRECT_LOGIN_OR_PASSWORD_MESSAGE;
import static by.epam.webtask.controller.PropertiesKey.USER_BLOCKED_MESSAGE;

public class SignInCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router = new Router();
        String login = request.getParameter(LOGIN);
        String pass = request.getParameter(PASSWORD);
        logger.log(Level.INFO, "login and pass" + login + pass);
        try {
            Optional<User> optionalUser = userService.signIn(login, pass);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                logger.log(Level.INFO, "Sign in" + user.getRole());
                switch (user.getRole()) {
                    case ADMIN -> {
                        router.setCurrentPage(ADMIN_PAGE);
                    }
                    case CLIENT -> {
                        if (user.getState() == User.UserState.BLOCKED) {
                            session.setAttribute(USER_STATUS_BLOCKED, USER_BLOCKED_MESSAGE);
                            router.setCurrentPage(SIGN_PAGE);
                        } else {
                            logger.log(Level.INFO, "Client page");
                            router.setCurrentPage(CLIENT_PAGE);
                        }
                    }
                    case TRAINER -> {
                        if (user.getState() == User.UserState.BLOCKED) {
                            session.setAttribute(USER_STATUS_BLOCKED, USER_BLOCKED_MESSAGE);
                            router.setCurrentPage(TRAINER_PAGE);
                        }
                    }
                }
            } else {
                logger.log(Level.DEBUG, "SignInCommand");
                request.setAttribute(ERROR_LOGIN_OR_PASSWORD, ERROR_INCORRECT_LOGIN_OR_PASSWORD_MESSAGE);
                router.setCurrentPage(SIGN_PAGE);
            }
        } catch (ServiceException e) {
            throw new CommandException("Error during sign in", e);
        }
        logger.log(Level.INFO, "SignInCommand");
        return router;
    }
}
