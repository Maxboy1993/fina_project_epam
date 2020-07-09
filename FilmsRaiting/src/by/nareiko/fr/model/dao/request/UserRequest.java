package by.nareiko.fr.model.dao.request;

public class UserRequest {
    private UserRequest(){}

    private static final int INACTIVE_STATUS_ID = 2;
    //requests
    public static final String FIND_ALL_USERS = "SELECT User.userId, User.firstName, User.lastName, User.birthday, UserRole.role, " +
            "User.login, User.password, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId";
    public static final String FIND_USER_BY_LOGIN = "SELECT User.userId, User.firstName, User.lastName, User.birthday, UserRole.role, " +
           "User.login, User.password, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
           "JOIN UserRole ON User.roleId = UserRole.roleId WHERE User.login = ?";
    public static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT User.userId, User.firstName, User.lastName, User.birthday, UserRole.role, " +
            "User.login, User.password, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId WHERE User.login = ? AND User.password = ?";
    public static final String FIND_USER_BY_ID = "SELECT User.userId, User.firstName, User.lastName, User.birthday, UserRole.role, " +
            "User.login, User.password, Status.status FROM User JOIN Status ON User.statusId = Status.statusId " +
            "JOIN UserRole ON User.roleId = UserRole.roleId WHERE User.userId = ?";
    public static final String DELETE_USER_BY_ID = "UPDATE user SET statusId = " + INACTIVE_STATUS_ID + " WHERE userId = ?";
    public static final String DELETE_USER_BY_LOGIN = "UPDATE user SET statusId = " + INACTIVE_STATUS_ID + " WHERE login = ?";
    public static final String CREATE_USER = "INSERT INTO user (firstName, lastName, birthday, roleId, " +
            "login, password, statusId) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_USER = "UPDATE user SET firstNmae = ?, lastName = ?, birthday = ?" +
            "roleId = ?, login = ?, password = ?, WHERE userId = ?";
}
