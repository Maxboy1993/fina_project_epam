package by.nareiko.fr.controller.command;

import by.nareiko.fr.controller.command.impl.DefaultCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Command provider.
 */
public class CommandProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Define command command.
     *
     * @param commandName the command name
     * @return the command
     */
    public static Command defineCommand(String commandName) {
        Command currentCommand;
        Command defaultCommand = new DefaultCommand();
        if (commandName == null || commandName.isBlank()) {
            return defaultCommand;
        }
        try {
            CommandType commandType = CommandType.valueOf(commandName.toUpperCase());
            currentCommand = commandType.getCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.error("CommandType definition exception", e);
            currentCommand = defaultCommand;
        }
        return currentCommand;
    }

}
