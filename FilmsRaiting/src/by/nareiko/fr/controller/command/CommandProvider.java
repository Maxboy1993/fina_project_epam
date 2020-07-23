package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.command.impl.DefaultCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CommandProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Command defineCommand(String commadName) {
        Command currentCommand;
        Command defaultCommand = new DefaultCommand();
        if (commadName == null || commadName.isBlank()) {
            return defaultCommand;
        }
        try {
            CommandType commandType = CommandType.valueOf(commadName.toUpperCase());
            currentCommand = commandType.getCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.error("CommandType definition exception", e);
            currentCommand = defaultCommand;
        }
        return currentCommand;
    }

}
