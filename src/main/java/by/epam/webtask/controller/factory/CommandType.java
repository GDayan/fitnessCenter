package by.epam.webtask.controller.factory;

import by.epam.webtask.controller.command.Command;
import by.epam.webtask.controller.command.impl.SignInCommand;
import by.epam.webtask.controller.command.impl.SignOutCommand;

import java.util.Optional;

public enum CommandType {
    SIGN_IN(new SignInCommand()),
    SIGN_OUT(new SignOutCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Optional<Command> provideCommand(String command) {
        Optional<Command> resultCommand;
        if (command == null || command.isEmpty()) {
            return Optional.empty();
        }
        try {
            CommandType commandType = CommandType.valueOf(command.toUpperCase());
            resultCommand = Optional.of(commandType.getCommand());
        } catch (IllegalArgumentException exp) {
            resultCommand = Optional.empty();
        }
        return resultCommand;
    }
}
