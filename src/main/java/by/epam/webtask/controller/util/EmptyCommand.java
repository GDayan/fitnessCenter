package by.epam.webtask.controller.util;

import by.epam.webtask.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.login");
        return page;
    }
}
