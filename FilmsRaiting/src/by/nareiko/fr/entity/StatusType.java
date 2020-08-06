package by.nareiko.fr.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The enum Status type.
 */
public enum StatusType {
    /**
     * Active status type.
     */
    ACTIVE("active"),
    /**
     * Inactive status type.
     */
    INACTIVE("inactive");

    private static final Logger LOGGER = LogManager.getLogger();

    private String statusType;

    StatusType(String statusType) {
        this.statusType = statusType;
    }

    /**
     * Gets status type by value.
     *
     * @param value the value
     * @return the status type by value
     */
    public static StatusType getStatusTypeByValue(String value) {
        StatusType[] statusTypes = StatusType.values();
        for (StatusType status : statusTypes) {
            if (status.getStatusType().equalsIgnoreCase(value)) {
                return status;
            }
        }
        LOGGER.error("Illegal argument, required status doesn't exist. Returned defualt status - active.");
        return ACTIVE;
    }

    /**
     * Gets status type.
     *
     * @return the status type
     */
    public String getStatusType() {
        return statusType;
    }
}
