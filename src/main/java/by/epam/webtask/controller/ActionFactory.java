package by.epam.webtask.controller;

import by.epam.webtask.controller.command.Command;
import by.epam.webtask.controller.command.CommandType;
import by.epam.webtask.controller.util.EmptyCommand;
import by.epam.webtask.controller.util.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    public Command defineCommand(HttpServletRequest request) {
        Command current = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentType = CommandType.valueOf(action.toUpperCase());
            current = currentType.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", action
                    + MessageManager.getProperty("message.wrongaction"));
        }
        return current;
    }
}
