package by.nareiko.fr.entity;

import by.nareiko.fr.exception.EntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum StatusType {
    ACTIVE("active"),
    INACTIVE("inactive");

    private static final Logger LOGGER = LogManager.getLogger();

    private String statusType;

    StatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getStatusType() {
        return statusType;
    }

    public static StatusType getStatusTypeByValue(String value) {
        StatusType[] statusTypes = StatusType.values();
        for (StatusType status : statusTypes) {
            if (status.getStatusType().equals(value)) {
                return status;
            }
        }
        LOGGER.error("Illegal argument, required status doesn't exist. Returned defualt status - active.");
        return ACTIVE;
    }
}
