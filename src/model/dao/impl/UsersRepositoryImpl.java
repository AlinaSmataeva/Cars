package model.dao.impl;

import entity.Car;
import entity.Role;
import entity.Users;
import exception.DaoException;
import model.connection.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl {
    private static final Logger logger = LogManager.getLogger(UsersRepositoryImpl.class);
    private static final CarRepositoryImpl instance = new CarRepositoryImpl();

    private static final String SIGN_IN = """
            SELECT u.id_user, u.username, u.password, r.id_role, r.description
            FROM Users u JOIN Role r ON u.role_id = r.id_role
            WHERE u.username = ? AND u.password = ?
            """;
    private static final String GET_ALL_USERS = """
            SELECT u.id_user, u.username, u.password, r.id_role 
            FROM Users u JOIN Role r ON u.role_id = r.id_role
            """;
    private static final String SIGN_UP = """
            insert into users(username, password, role_id) values(?, ?, (select id_role from role where description = ?))
            """;
    public Optional<Users> signIn(String username, String password) throws DaoException {
        Users user = new Users();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SIGN_IN)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getLong("id_role"));
                role.setDescription(resultSet.getString("description"));

                user.setId(resultSet.getLong("id_user"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(role);
            }
            return Optional.of(user);
        } catch (SQLException sqlException) {
            logger.log(Level.ERROR, "Cannot proceed request: " + SIGN_IN);
            throw new DaoException("Cannot proceed request");
        }
    }
    public List<Users> getAllUsers() throws DaoException {
        List<Users> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Users user = new Users();
                user.setId(resultSet.getLong("id_user"));
                user.setUsername(resultSet.getString("username"));
                users.add(user);
            }
        } catch (SQLException sqlException) {
            logger.log(Level.ERROR, "Cannot proceed request: " + GET_ALL_USERS);
            throw new DaoException("Cannot proceed request");
        }
        return users;
    }

    public boolean signUp(String username, String password) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();//подключаемся к бд
             PreparedStatement statement = connection.prepareStatement(SIGN_UP)) { //посылаем запрос
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "Пользователь");

            return (statement.executeUpdate() == 1);
        } catch (SQLException sqlException) {
            logger.log(Level.ERROR, "Can not proceed request: " + SIGN_UP);
            throw new DaoException("Cannot proceed request");
        }
    }
}
