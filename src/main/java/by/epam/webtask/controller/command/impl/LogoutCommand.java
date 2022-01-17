package by.epam.webtask.controller.command.impl;

import by.epam.webtask.controller.util.ConfigurationManager;
import by.epam.webtask.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.index");
        request.getSession().invalidate();
        return page;
    }
}