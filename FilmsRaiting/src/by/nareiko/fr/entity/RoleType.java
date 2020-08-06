package by.nareiko.fr.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The enum Role type.
 */
public enum RoleType {
    /**
     * Guest role type.
     */
    GUEST("guest"),
    /**
     * Admin role type.
     */
    ADMIN("admin"),
    /**
     * User role type.
     */
    USER("user");

    private static final Logger LOGGER = LogManager.getLogger();

    private String roleType;

    RoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * Gets role type by value.
     *
     * @param value the value
     * @return the role type by value
     */
    public static RoleType getRoleTypeByValue(String value) {
        RoleType[] roleTypes = RoleType.values();
        for (RoleType role : roleTypes) {
            if (role.getRoleType().equalsIgnoreCase(value)) {
                return role;
            }
        }
        LOGGER.error("Illegal argument, required role doesn't exist. Returned default role - user.");
        return USER;
    }

    /**
     * Gets role type.
     *
     * @return the role type
     */
    public String getRoleType() {
        return roleType;
    }
}
