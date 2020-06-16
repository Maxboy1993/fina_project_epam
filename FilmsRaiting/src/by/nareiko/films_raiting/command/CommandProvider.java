package by.nareiko.films_raiting.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CommandProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Optional<Command> defineCommand(String commadName) {
        Optional<Command> currentCommand;
        if (commadName == null || commadName.isBlank()) {
            return Optional.empty();
        }
        try {
            CommandType commandType = CommandType.valueOf(commadName.toUpperCase());
            currentCommand = Optional.of(commandType.getCommand());
        } catch (IllegalArgumentException e) {
            LOGGER.error("CommandType definition exception", e);
            currentCommand = Optional.empty();
        }
        return currentCommand;
    }

}
