package model.dao;

import entity.Car;
import exception.DaoException;

import java.util.List;

public interface CarRepository {
    List<Car> getCarById(Long carId) throws DaoException;
    List<Car> getAllCars() throws DaoException;
}
