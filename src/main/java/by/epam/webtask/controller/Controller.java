package by.epam.webtask.controller;

import by.epam.webtask.controller.command.Command;
import by.epam.webtask.controller.factory.CommandType;
import by.epam.webtask.exception.CommandException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.util.Optional;

import static by.epam.webtask.controller.Parameter.COMMAND;
import static by.epam.webtask.controller.PathPage.ERROR_500;

@WebServlet(urlPatterns = {"/controller"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 15,
        maxRequestSize = 1024 * 1024 * 15 * 10)
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    @Serial
    private static final long serialVersionUID = -7848771821471376473L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.DEBUG, "It's a " + request.getMethod());
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.DEBUG, "It's a " + request.getMethod());
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(COMMAND);
        logger.log(Level.DEBUG, "The command is " + commandName);
        Optional<Command> command = CommandType.provideCommand(commandName);
        try {
            Router router;
            if (command.isPresent()) {
                router = command.get().execute(request);
                String page = router.getCurrentPage();
                if (router.getCurrentType() == Router.Type.FORWARD) {
                    logger.log(Level.INFO, "Forward type.");
                    request.getRequestDispatcher(page).forward(request, response);
                } else {
                    logger.log(Level.INFO, "Redirect type.");
                    response.sendRedirect(page);
                }
            } else {
                response.sendRedirect(ERROR_500);
            }
        } catch (CommandException e) {
            logger.log(Level.ERROR, e.getMessage());
            response.sendRedirect(ERROR_500);
        }

    }
}
