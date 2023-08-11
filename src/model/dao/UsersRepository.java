package model.dao;


import entity.Users;
import exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    Optional<Users> signIn(String username, String password) throws DaoException;
    List<Users> getAllUsers() throws DaoException;
    boolean signUp(String username, String password) throws DaoException;
}
