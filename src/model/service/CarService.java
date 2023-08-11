package model.service;

import entity.Car;
import exception.DaoException;
import exception.ServiceException;
import model.dao.CarRepository;

import java.util.List;

public class CarService {
    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCarById(Long carId) throws ServiceException {
        try {
            return carRepository.getCarById(carId);
        } catch (DaoException e) {
            throw new ServiceException("Error retrieving car by ID", e);
        }
    }

    public List<Car> getAllCars() throws ServiceException {
        try {
            return carRepository.getAllCars();
        } catch (DaoException e) {
            throw new ServiceException("Error retrieving all cars", e);
        }
    }
}