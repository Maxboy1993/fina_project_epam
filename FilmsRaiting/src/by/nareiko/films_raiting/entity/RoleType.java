package by.nareiko.films_raiting.entity;

import by.nareiko.films_raiting.exception.EntityException;

public enum  RoleType  {
    ADMIN("admin"),
    USER("user");

    private String roleType;

    RoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleType(){
        return roleType;
    }

    public static RoleType getRoleTypeByValue(String value) throws EntityException {
        RoleType[] roleTypes = RoleType.values();
        for (RoleType role : roleTypes) {
            if (role.getRoleType().equals(value)){
                return role;
            }
        }
        throw new EntityException("Illegal argument");
    }
}