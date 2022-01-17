package by.epam.webtask.controller.command.impl;

import by.epam.webtask.controller.util.ConfigurationManager;
import by.epam.webtask.controller.util.LoginLogic;
import by.epam.webtask.controller.util.MessageManager;
import by.epam.webtask.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final String NAME_LOGIN = "login";
    private static final String NAME_PASSWORD = "password";
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(NAME_LOGIN);
        String pass = request.getParameter(NAME_PASSWORD);
        if (LoginLogic.checkLogin(login, pass)) {
            request.setAttribute("user", login);
            page = ConfigurationManager.getProperty("path.page.main");
        } else {
            request.setAttribute("errorLoginPassMessage",
                    MessageManager.getProperty("message.loginerror"));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }

}
