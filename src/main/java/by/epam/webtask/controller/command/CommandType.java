package by.epam.webtask.controller.command;

import by.epam.webtask.controller.util.EmptyCommand;
import by.epam.webtask.controller.command.impl.LoginCommand;
import by.epam.webtask.controller.command.impl.LogoutCommand;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    EMPTY {
        {
            this.command = new EmptyCommand();
        }
    };
    Command command;
    public Command getCurrentCommand() {
        return command;
    }

    public static Command defineCommand(String requestCommand){
        Command command;
        CommandType currentEnum;
        try {
            currentEnum = CommandType.valueOf(requestCommand.toUpperCase());
        } catch (IllegalArgumentException e){
            currentEnum = CommandType.EMPTY;
        }
        command =currentEnum.getCurrentCommand();
        return command;
    }

}
