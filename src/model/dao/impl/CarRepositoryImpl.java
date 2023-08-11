package model.dao.impl;

import entity.Car;
import exception.DaoException;
import model.connection.ConnectionPool;
import model.dao.CarRepository;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class CarRepositoryImpl implements CarRepository {
    private static final Logger logger = LogManager.getLogger(CarRepository.class);
    private static final CarRepositoryImpl instance = new CarRepositoryImpl();
    private static final String GET_CAR_BY_ID = """
            SELECT id_car, name, type, countExamples
            FROM Car WHERE id = ?
            """;
    private static final String GET_ALL_CARS = """
            SELECT id_car, name, type, countExamples
            FROM Car""";

    @Override
    public List<Car> getCarById(Long carId) throws DaoException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_CAR_BY_ID)) {
            statement.setLong(1, carId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Car car = new Car();
                    car.setId(resultSet.getLong("id_car"));
                    car.setName(resultSet.getString("name"));
                    car.setType(resultSet.getString("type"));
                    car.setCountExamples(resultSet.getInt("countExamples"));
                    cars.add(car);
                }
            }
        } catch (SQLException sqlException) {
            logger.log(Level.ERROR, "Cannot proceed request: " + GET_CAR_BY_ID);
            throw new DaoException("Cannot proceed request");
        }
        return cars;
    }

    @Override
    public List<Car> getAllCars() throws DaoException {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_CARS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getLong("id_car"));
                car.setName(resultSet.getString("name"));
                car.setType(resultSet.getString("type"));
                car.setCountExamples(resultSet.getInt("countExamples"));
                cars.add(car);
            }
        } catch (SQLException sqlException) {
            logger.log(Level.ERROR, "Cannot proceed request: " + GET_ALL_CARS);
            throw new DaoException("Cannot proceed request");
        }
        return cars;
    }
}

