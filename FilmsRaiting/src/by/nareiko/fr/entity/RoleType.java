package by.nareiko.fr.entity;

import by.nareiko.fr.exception.EntityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum RoleType {
    GUEST("guest"),
    ADMIN("admin"),
    USER("user");

    private static final Logger LOGGER = LogManager.getLogger();

    private String roleType;

    RoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleType() {
        return roleType;
    }

    public static RoleType getRoleTypeByValue(String value) {
        RoleType[] roleTypes = RoleType.values();
        for (RoleType role : roleTypes) {
            if (role.getRoleType().equals(value)) {
                return role;
            }
        }
        LOGGER.error("Illegal argument, required role doesn't exist. Returned defualt role - user.");
        return USER;
    }
}
