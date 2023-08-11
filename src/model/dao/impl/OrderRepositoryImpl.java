package model.dao.impl;

import entity.Order;
import exception.DaoException;
import model.connection.ConnectionPool;
import model.dao.OrderRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderRepositoryImpl implements OrderRepository {
    private static final Logger logger = LogManager.getLogger(OrderRepositoryImpl.class);

    private static final String INSERT_ORDER = """
            INSERT INTO Orders (user_id, car_id)
            VALUES (?, ?)
            """;
    @Override
    public boolean createOrder(Order order) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER)) {
            statement.setLong(1, order.getUserId());
            statement.setLong(2, order.getCarId());

            return (statement.executeUpdate() == 1);
        } catch (SQLException sqlException) {
            logger.log(Level.ERROR, "Cannot proceed request: " + INSERT_ORDER);
            throw new DaoException("Cannot proceed request", sqlException);
        }
    }
}
