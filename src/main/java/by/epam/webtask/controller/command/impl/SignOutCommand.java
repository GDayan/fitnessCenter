package by.epam.webtask.controller.command.impl;

import by.epam.webtask.controller.Router;
import by.epam.webtask.controller.command.Command;
import by.epam.webtask.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.epam.webtask.controller.PathPage.GUEST_PAGE;

public class SignOutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router();
        HttpSession session = request.getSession();
        session.invalidate();
        router.setRedirectType();
        router.setCurrentPage(GUEST_PAGE);
        return router;
    }
}
