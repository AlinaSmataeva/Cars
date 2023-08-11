package model.service;

import entity.Users;
import exception.DaoException;
import exception.ServiceException;
import model.dao.UsersRepository;
import model.dao.impl.UsersRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class UsersService {
    private UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Method for user sign in
    public Optional<Users> signIn(String username, String password) throws ServiceException {
        try {
            return usersRepository.signIn(username, password);
        } catch (DaoException e) {
            throw new ServiceException("Error during sign in", e);
        }
    }

    // Method to get all users
    public List<Users> getAllUsers() throws ServiceException {
        try {
            return usersRepository.getAllUsers();
        } catch (DaoException e) {
            throw new ServiceException("Error retrieving all users", e);
        }
    }

    // Method to sign up a new user
    public boolean signUp(String username, String password) throws ServiceException {
        try {
            return usersRepository.signUp(username, password);
        } catch (DaoException e) {
            throw new ServiceException("Error during sign up", e);
        }
    }
}
